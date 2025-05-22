package com.ciocmih.store_management.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductDTO(
        @NotNull(message = "Product price must be set.")
        @Positive(message = "Product price must be a positive number.")
        Double price,

        @NotNull(message = "Product quantity must be set.")
        @PositiveOrZero(message = "Product quantity must be a positive number.")
        Integer quantity
) {
}
