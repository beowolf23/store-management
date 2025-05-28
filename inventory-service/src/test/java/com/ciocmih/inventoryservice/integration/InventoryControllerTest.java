package com.ciocmih.inventoryservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void when_gettingInventoryForProduct_thenReturnsInventoryData() throws Exception {
        mockMvc.perform(get("/api/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory for: 1"));
    }

    @Test
    void when_addingProductToInventory_thenReturnsProduct() throws Exception {
        String productJson = """
                {
                    "id":       1,
                    "name": "Test Product",
                    "quantity": 100
                }
                """;

        mockMvc.perform(post("/api/inventory")
                        .contentType("application/json")
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(content().string(productJson));
    }

    @Test
    void when_updatingProductInventory_thenReturnsUpdatedProduct() throws Exception {
        String updateJson = """
                {
                    "quantity": 150
                }
                """;

        mockMvc.perform(put("/api/inventory/1")
                        .contentType("application/json")
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated inventory for product: 1"));
    }
}
