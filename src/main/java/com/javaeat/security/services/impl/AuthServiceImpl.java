package com.javaeat.security.services.impl;


import com.javaeat.enums.CartStatus;
import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Cart;
import com.javaeat.model.Customer;
import com.javaeat.repository.CustomerRepository;
import com.javaeat.security.dto.*;
import com.javaeat.security.enums.Role;
import com.javaeat.security.model.Token;
import com.javaeat.security.model.User;
import com.javaeat.security.repository.TokenRepository;
import com.javaeat.security.repository.UserRepository;
import com.javaeat.security.services.AuthService;
import com.javaeat.security.services.JwtService;
import com.javaeat.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.security.Principal;

import static com.javaeat.security.enums.TokenType.BEARER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final MapperUtil mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse register(SignUpDto signUpDto) {
        isUserExists(signUpDto.getEmail());
        User user = mapper.mapEntity(signUpDto, User.class);
        user.setRole(Role.CUSTOMER);
        user.setPassword(encoder.encode(signUpDto.getPassword()));
        setUpUser(user);
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return new AuthResponse(jwtToken, refreshToken);
    }

    public void setUpUser(User user) {
        Cart cart = new Cart();
        Customer customer = new Customer();

        customer.setCart(cart);
        customer.setUser(user);
        cart.setTotalPrice(0.0);
        cart.setStatus(CartStatus.READ_WRITE);
        cart.setTotalItems(0);
        cart.setCustomer(customer);

        customerRepository.save(customer);
    }

    private void saveUserToken(User user, String token) {
        if (tokenRepository.findByUser(user).isEmpty()) {
            Token buildToken = Token.builder()
                    .user(user)
                    .token(token)
                    .tokenType(BEARER)
                    .isExpired(false)
                    .isRevoked(false)
                    .build();
            tokenRepository.save(buildToken);
        }
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword()));
        User user = repository.findByEmail(authRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return new AuthResponse(jwtToken, refreshToken);
    }

    @Override
    public AuthResponse generateRefreshToken(TokenRequest tokenRequest) {
        String email = jwtService.extractUsername(tokenRequest.getToken());
        User user = repository.findByEmail(email).orElseThrow();
        if (jwtService.isTokenValid(tokenRequest.getToken(), user)) {
            String jwt = jwtService.generateToken(user);
            return new AuthResponse(jwt, tokenRequest.getToken());
        }
        return null;
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(encoder.encode(request.getNewPassword()));
        repository.save(user);
    }

    @PostConstruct
    void addAdminAccount() {
        User adminAccount = repository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setFirstname("user");
            user.setLastname("user");
            user.setRole(Role.ADMIN);
            user.setEmail("admin@javeats.com");
            user.setRole(Role.ADMIN);
            user.setPassword(encoder.encode("admin"));
            repository.save(user);
        }
    }

    private void isUserExists(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.USER_ALREADY_EXISTS.name());
        }
    }
}
