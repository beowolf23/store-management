package com.ciocmih.store_management.controller;

import com.ciocmih.store_management.dto.ApiPageableResponse;
import com.ciocmih.store_management.dto.ApiResponse;
import com.ciocmih.store_management.dto.product.CreateProductDTO;
import com.ciocmih.store_management.dto.product.UpdateProductDTO;
import com.ciocmih.store_management.model.Product;
import com.ciocmih.store_management.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Integer productId, @Valid @RequestBody UpdateProductDTO dto) {
        Product product = productService.updateProduct(productId, dto);
        ApiResponse<Product> response = ApiResponse.<Product>builder()
                .data(product)
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Product>> addProduct(@Valid @RequestBody CreateProductDTO dto) {
        Product product = productService.addProduct(dto);
        ApiResponse<Product> response = ApiResponse.<Product>builder()
                .data(product)
                .statusCode(HttpStatus.CREATED.value())
                .success(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProduct(productId);

        ApiResponse<Product> response = ApiResponse.<Product>builder()
                .data(product)
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiPageableResponse<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> productPage = productService.getAllProductsWithPagination(page, size);

        ApiPageableResponse<Product> response = new ApiPageableResponse<>(
                productPage,
                HttpStatus.OK.value(),
                true
        );

        return ResponseEntity.ok(response);
    }
}
