package com.ciocmih.productservice.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final RestTemplate restTemplate;

    public Integer getQuantity(Integer productId) {
        String url = "http://inventory-service:8080/api/inventory/" + productId + "/quantity";
        return restTemplate.getForObject(url, Integer.class);
    }
}