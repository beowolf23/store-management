package com.ciocmih.productservice.service;

import com.ciocmih.productservice.dto.product.CreateProductDTO;
import com.ciocmih.productservice.dto.product.UpdateProductDTO;
import com.ciocmih.productservice.exception.DuplicateProductException;
import com.ciocmih.productservice.exception.ProductNotFoundException;
import com.ciocmih.productservice.model.Product;
import com.ciocmih.productservice.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Integer productId) throws ProductNotFoundException {
        return productRepository
                .findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Page<Product> getAllProductsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
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
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException(e);
        }
        return product;
    }
}
