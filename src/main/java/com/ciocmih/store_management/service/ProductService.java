package com.ciocmih.store_management.service;

import com.ciocmih.store_management.dto.UpdateProductDTO;
import com.ciocmih.store_management.exception.ProductNotFoundException;
import com.ciocmih.store_management.model.Product;
import com.ciocmih.store_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product updateProduct(Integer productId, UpdateProductDTO dto) {
        return productRepository
                .findById(productId)
                .map(product -> {
                    product.setQuantity(dto.quantity());
                    product.setPrice(dto.price());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
