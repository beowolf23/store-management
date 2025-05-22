package com.ciocmih.store_management.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {
}
