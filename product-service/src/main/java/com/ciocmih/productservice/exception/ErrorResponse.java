package com.ciocmih.productservice.exception;

import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorResponse(
        Date timestamp,
        String status,
        String[] errors,
        String path
) {
}
