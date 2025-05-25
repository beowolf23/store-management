package com.ciocmih.productservice.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.getMessage());
    }
}
