package com.ciocmih.productservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessages {

    PRODUCT_NOT_FOUND("Product not found"),
    DUPLICATE_PRODUCT_FOUND("Product with the same name already exists.");

    private final String message;

}
