package com.ciocmih.store_management.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank(message = "Product name must not be blank")
        @Size(max = 100, message = "Product name must be at most 100 characters")
        String name,

        @Size(max = 255, message = "Description must be at most 255 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive number")
        @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")

        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Min(value = 0, message = "Quantity must be zero or more")
        Integer quantity
) {
}
