package com.javaeat.util;

import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.CartItemResponse;
import com.javaeat.response.CartResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class MapperUtil {
	private static final ModelMapper mapper = new ModelMapper();

	public static Cart mapToEntity(CartRequest request) {
		return mapper.map(request, Cart.class);
	}

	public static CartRequest mapToRequest(Cart cart) {
		return mapper.map(cart, CartRequest.class);
	}

	public static CartItem mapToEntity(CartItemRequest request) {
		return mapper.map(request, CartItem.class);
	}

	public static CartItemRequest mapToRequest(CartItem cartItem) {
		return mapper.map(cartItem, CartItemRequest.class);
	}

	public static CartResponse mapToResponse(Cart cart) {
		return mapper.map(cart, CartResponse.class);
	}

	public static CartItemResponse mapToResponse(CartItem cartItem) {
		return mapper.map(cartItem, CartItemResponse.class);
	}
	
	public static List<CartItemResponse> mapToCartItemsResponse(List<CartItem> cartItems){
		return cartItems.stream().map(cartItem -> mapToResponse(cartItem)).collect(Collectors.toList());
	}
	

}
