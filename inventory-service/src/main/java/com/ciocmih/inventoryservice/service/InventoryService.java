package com.ciocmih.inventoryservice.service;

import com.ciocmih.inventoryservice.exception.ProductNotFoundException;
import com.ciocmih.inventoryservice.model.Item;
import com.ciocmih.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Integer getProductInventory(Long productId) {
        return inventoryRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId))
                .getQuantity();
    }

    public Item addProductInventory(Item item) {
        return inventoryRepository.save(item);
    }
}
