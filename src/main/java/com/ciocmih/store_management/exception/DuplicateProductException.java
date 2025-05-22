package com.ciocmih.store_management.exception;

public class DuplicateProductException extends RuntimeException {

    public DuplicateProductException(Throwable cause) {
        super(ExceptionMessages.DUPLICATE_PRODUCT_FOUND.getMessage(), cause);
    }
}
