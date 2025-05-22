package com.ciocmih.store_management.service;

import com.ciocmih.store_management.dto.CreateProductDTO;
import com.ciocmih.store_management.dto.UpdateProductDTO;
import com.ciocmih.store_management.exception.DuplicateProductException;
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
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product addProduct(CreateProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price().doubleValue());
        product.setQuantity(dto.quantity());

        try {
            product = productRepository.save(product);
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key")) {
                throw new DuplicateProductException(e);
            }
        }
        return product;
    }
}
