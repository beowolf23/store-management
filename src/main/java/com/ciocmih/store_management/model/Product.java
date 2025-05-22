package com.ciocmih.store_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "products")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "Product name is required.")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Product description is required.")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Product price must be a number.")
    @Positive(message = "Product price must be a positive number.")
    private Double price;

    @Column(name = "quantity")
    @NotNull(message = "Product quantity must be a number.")
    @PositiveOrZero(message = "Product quantity must be a positive number.")
    private Integer quantity;

    @Version
    @Column(name = "version")
    private Integer version;
}
