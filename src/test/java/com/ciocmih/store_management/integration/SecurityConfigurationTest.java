package com.ciocmih.store_management.integration;

import com.ciocmih.store_management.dto.product.CreateProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SecurityConfigurationTest {

    @Container
    public static DockerComposeContainer<?> environment =
            new DockerComposeContainer<>(new File("src/test/resources/compose.yaml"))
                    .withExposedService("postgres", 5432, Wait.forListeningPort());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/products";
    }

    @Test
    void when_accessingEndpointWithoutAuthentication_then_unauthorized() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void when_adminAccessesAdminEndpoint_then_okOrBadRequest() {
        CreateProductDTO dto = new CreateProductDTO(
                "iPhone 14 Pro Max",
                "A kind of phone that you will never regret buying.",
                BigDecimal.valueOf(200),
                200
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateProductDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("admin", "adminpass")
                .postForEntity(baseUrl(), request, String.class);
        assertThat(response.getStatusCode()).isIn(HttpStatus.CREATED);
    }

    @Test
    void when_userAccessesAdminEndpoint_then_forbidden() {
        CreateProductDTO dto = new CreateProductDTO(
                "iPhone 14 Pro Max",
                "A kind of phone that you will never regret buying.",
                BigDecimal.valueOf(200),
                200
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateProductDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "userpass")
                .postForEntity(baseUrl(), request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void when_userAccessesUserEndpoint_then_ok() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "userpass")
                .getForEntity(baseUrl(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
