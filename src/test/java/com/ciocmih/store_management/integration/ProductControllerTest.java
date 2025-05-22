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

import java.util.Arrays;

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

        UpdateProductDTO dto = new UpdateProductDTO(10., 19);

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
        assertThat(updated.getPrice()).isEqualTo(10.);
        assertThat(updated.getQuantity()).isEqualTo(19);
        assertThat(updated.getVersion()).isEqualTo(1);
    }

    @Test
    void when_updatingANonExistingProduct_then_not_and_returnsErrorMessage() {

        UpdateProductDTO dto = new UpdateProductDTO(10., 19);

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
        ErrorResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.errors()[0]).isEqualTo("Product not found");
    }

    @Test
    void when_updatingProductWithInvalidData_then_badRequest_and_returnsErrorMessage() {
        Product product = productRepository.findAll().getFirst();

        UpdateProductDTO dto = new UpdateProductDTO(-19.99, -10);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateProductDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl + "/" + product.getId(),
                HttpMethod.PUT,
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        ErrorResponse body = response.getBody();
        assertThat(body.errors()).isNotNull();
        assertThat(body.errors()).hasSize(2);

        assertThat(
                Arrays.stream(body.errors())
                        .filter(error -> error.contains("price"))
                        .findFirst()
                        .orElseThrow()
        ).isEqualTo("price: Product price must be a positive number.");

        assertThat(
                Arrays.stream(body.errors())
                        .filter(error -> error.contains("quantity"))
                        .findFirst()
                        .orElseThrow()
        ).isEqualTo("quantity: Product quantity must be a positive number.");
    }

    @Test
    void when_creatingProductWithValidData_then_created_and_returnsCreatedProduct() {
        Product product = new Product();
        product.setName("Samsung Galaxy S24");
        product.setDescription("Okay phone.");
        product.setPrice(10.0);
        product.setQuantity(5);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<Product> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                Product.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Product createdProduct = response.getBody();
        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isNotNull();
    }

    @Test
    void when_creatingDuplicateProduct_then_conflict_and_returnsErrorMessage() {
        Product product = productRepository.findAll().getFirst();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                request,
                ErrorResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        ErrorResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.errors()[0]).isEqualTo("Product with the same name already exists.");
    }

}
