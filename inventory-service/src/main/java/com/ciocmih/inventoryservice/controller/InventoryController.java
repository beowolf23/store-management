package com.ciocmih.inventoryservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @GetMapping
    public String getInventory() {
        return "Inventory data";
    }

    @GetMapping("/{productId}")
    public String getInventoryForProduct(@PathVariable String productId) {
        return "Inventory for: " + productId;
    }

    @PostMapping
    public String addProductToInventory(@RequestBody String product) {
        return product;
    }

    @PutMapping("/{productId}")
    public String updateProductInventory(@PathVariable String productId, @RequestBody String update) {
        return "Updated inventory for product: " + productId;
    }

}
