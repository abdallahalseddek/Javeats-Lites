package com.javaeat.security.services;


import com.javaeat.enums.CartStatus;
import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Cart;
import com.javaeat.model.Customer;
import com.javaeat.repository.CustomerRepository;
import com.javaeat.security.dto.AuthRequest;
import com.javaeat.security.dto.AuthResponse;
import com.javaeat.security.dto.SignUpDto;
import com.javaeat.security.dto.TokenRequest;
import com.javaeat.security.enums.Role;
import com.javaeat.security.model.User;
import com.javaeat.security.repository.UserRepository;
import com.javaeat.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final MapperUtil mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;

    public User signUp(SignUpDto signUpDto) {
        isUserExists(signUpDto.getEmail());
        User user = mapper.mapEntity(signUpDto, User.class);
        user.setRole(Role.CUSTOMER);
        user.setPassword(encoder.encode(signUpDto.getPassword()));
        setUpUser(user);
        return repository.save(user);
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

    public AuthResponse signIn(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword()));
        User user = repository.findByEmail(authRequest.getEmail()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(jwt, refreshToken);
    }

    public AuthResponse generateRefreshToken(TokenRequest tokenRequest) {
        String email = jwtService.extractUsername(tokenRequest.getToken());
        User user = repository.findByEmail(email).orElseThrow();
        if (jwtService.isTokenValid(tokenRequest.getToken(), user)) {
            String jwt = jwtService.generateToken(user);
            return new AuthResponse(jwt, tokenRequest.getToken());
        }
        return null;
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
        repository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(HttpStatus.FORBIDDEN.value(),
                        ErrorMessage.USER_ALREADY_EXISTS.name()));
    }
}
