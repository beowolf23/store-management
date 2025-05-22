package com.ciocmih.store_management.controller;

import com.ciocmih.store_management.dto.CreateProductDTO;
import com.ciocmih.store_management.dto.UpdateProductDTO;
import com.ciocmih.store_management.model.Product;
import com.ciocmih.store_management.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<String>> hello() {
        return ResponseEntity.ok(List.of("Product1", "Product2", "Product3"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @Valid @RequestBody UpdateProductDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(productId, dto));
    }

    @PostMapping("")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody CreateProductDTO dto) {
        return new ResponseEntity<>(productService.addProduct(dto), HttpStatus.CREATED);
    }

}
