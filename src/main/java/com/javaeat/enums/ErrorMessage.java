package com.javaeat.enums;

public enum ErrorMessage {
	CART_ITEM_NOT_FOUND("Cart item not found"),
	CART_NOT_FOUND("Cart not found"),

	CART_ITEM_ALREADY_EXISTS("cart italready exists you cant add it")
	;

	private String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
