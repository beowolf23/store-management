package com.ciocmih.inventoryservice.controller;

import com.ciocmih.inventoryservice.model.Item;
import com.ciocmih.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String getInventory() {
        return "Inventory data";
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Item> getInventoryForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getProductInventory(productId));
    }

    @PostMapping
    public ResponseEntity<Item> addProductInventory(@RequestBody Item item) {
        return new ResponseEntity<>(inventoryService.addProductInventory(item), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public String updateProductInventory(@PathVariable String productId, @RequestBody String update) {
        return "Updated inventory for product: " + productId;
    }

}
