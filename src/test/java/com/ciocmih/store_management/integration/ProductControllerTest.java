package com.ciocmih.store_management.integration;

import com.ciocmih.store_management.dto.UpdateProductDTO;
import com.ciocmih.store_management.exception.ErrorResponse;
import com.ciocmih.store_management.model.Product;
import com.ciocmih.store_management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    private String baseUrl;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        baseUrl = "http://localhost:" + port + "/api/products";

        // Insert a test product
        Product p = new Product();
        p.setName("iPhone 14 Pro Max");
        p.setDescription("Great phone, need to jailbreak it.");
        p.setPrice(10.0);
        p.setQuantity(5);
        productRepository.save(p);
    }

    @Test
    void when_updatingExistingProduct_then_ok_and_returnsUpdatedProduct() {
        Product product = productRepository.findAll().getFirst();

        UpdateProductDTO dto = new UpdateProductDTO(10, 19.99);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateProductDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Product> response = restTemplate.exchange(
                baseUrl + "/" + product.getId(),
                HttpMethod.PUT,
                request,
                Product.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Product updated = response.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getPrice()).isEqualTo(19.99);
        assertThat(updated.getQuantity()).isEqualTo(10);
        assertThat(updated.getVersion()).isEqualTo(1);
    }

    @Test
    void when_updatingANonExistingProduct_then_not_and_returnsErrorMessage() {

        UpdateProductDTO dto = new UpdateProductDTO(10, 19.99);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateProductDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl + "/" + -1,
                HttpMethod.PUT,
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        ErrorResponse error = response.getBody();
        assertThat(error).isNotNull();
        assertThat(error.message()).isEqualTo("Product not found");
    }
}
