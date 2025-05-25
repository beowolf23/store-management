package com.ciocmih.productservice.config;

import com.ciocmih.productservice.model.Product;
import com.ciocmih.productservice.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductDataLoader(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            log.info("Loading initial product data...");
            loadProductsFromFile();
        } else {
            log.info("Products already exist in database. Skipping data loading.");
        }
    }

    private void loadProductsFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("data/products.json");

            if (!resource.exists()) {
                log.warn("products.json file not found in classpath.");
                return;
            }

            TypeReference<List<ProductDto>> typeReference = new TypeReference<>() {
            };
            List<ProductDto> productDtos = objectMapper.readValue(resource.getInputStream(), typeReference);

            List<Product> products = productDtos.stream()
                    .map(this::convertToEntity)
                    .toList();

            productRepository.saveAll(products);
            log.info("Successfully loaded {} products from file", products.size());

        } catch (IOException e) {
            log.error("Error loading products from file: {}", e.getMessage());
        }
    }

    private Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice().doubleValue());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDto {
        private String name;
        private String description;
        private BigDecimal price;
        private String category;
        private Integer quantity;
    }
}