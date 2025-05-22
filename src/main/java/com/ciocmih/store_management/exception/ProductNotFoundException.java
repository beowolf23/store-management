package com.ciocmih.store_management.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.getMessage());
    }
}
