package com.ciocmih.inventoryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items")
@RequiredArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;
}
