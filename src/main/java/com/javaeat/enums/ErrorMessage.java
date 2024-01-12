package com.javaeat.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    NOT_FOUND("Not Found"),

    RESTAURANT_ALREADY_EXISTS("Restaurant already exists"),

    RESTAURANT_NOT_FOUND("Restaurant not found"),

    MENU_NOT_FOUND("Menu not found"),

    MENU_ITEM_NOT_FOUND("Menu item not found"),

    MENU_ITEM_ALREADY_EXISTS("Menu item already exists"),

    MENU_ALREADY_EXISTS("Menu already exists"),

    CART_ITEM_NOT_FOUND("Cart item not found"),

    CART_NOT_FOUND("Cart not found"),

    CART_ITEM_ALREADY_EXISTS("cart item already exists you cant add it");

    private final String message;
    ErrorMessage(String message) {
        this.message = message;
    }

}
