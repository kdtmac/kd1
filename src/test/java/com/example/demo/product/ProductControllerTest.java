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
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProductList() throws Exception {
        String loginResponse = mockMvc.perform(post("/user/login")
                .contentType("application/json")
                .content("{\"username\":\"test\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
// 为了使用ObjectMapper，需要导入Jackson库中的ObjectMapper类
// 原代码
String token = new ObjectMapper().readTree(loginResponse).get("token").asText();

        mockMvc.perform(get("/products")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void testPurchaseProduct() throws Exception {
        String loginResponse = mockMvc.perform(post("/user/login")
                .contentType("application/json")
                .content("{\"username\":\"test\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String token = new ObjectMapper().readTree(loginResponse).get("token").asText();

        mockMvc.perform(post("/purchase")
                .header("Authorization", token)
                .param("username", "testUser")
                .param("productId", "1")
                .param("quantity", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPurchaseProductWithInsufficientStock() throws Exception {
        String loginResponse = mockMvc.perform(post("/user/login")
                .contentType("application/json")
                .content("{\"username\":\"test\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String token = new ObjectMapper().readTree(loginResponse).get("token").asText();

        mockMvc.perform(post("/purchase")
                .header("Authorization", token)
                .param("username", "testUser")
                .param("productId", "1")
                .param("quantity", "1000"))
                .andExpect(status().isBadRequest());
    }
}