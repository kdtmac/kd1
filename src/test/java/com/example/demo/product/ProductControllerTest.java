package com.example.demo.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // 添加静态导入

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProductList() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void testPurchaseProduct() throws Exception {
        mockMvc.perform(post("/purchase")
                .param("username", "testUser")
                .param("productId", "1")
                .param("quantity", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPurchaseProductWithInsufficientStock() throws Exception {
        mockMvc.perform(post("/purchase")
                .param("username", "testUser")
                .param("productId", "1")
                .param("quantity", "1000"))
                .andExpect(status().isBadRequest());
    }
}