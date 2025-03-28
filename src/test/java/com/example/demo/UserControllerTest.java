package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试用户注册接口
     * <p>
     * 测试流程：
     * 1. 构造测试用户JSON数据
     * 2. 发起POST请求到/user/register端点
     * 3. 验证响应状态码为200
     * 4. 验证响应内容为注册成功消息
     *
     * @throws Exception 测试过程中可能抛出的异常
     * @see UserController#register(User) 对应的控制器方法
     */
    @Test
    public void testRegister() throws Exception {
        String userJson = "{\"username\":\"test\",\"password\":\"pass\"}";
        
        mockMvc.perform(post("/user/register")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    /**
     * 测试用户登录接口
     * <p>
     * 测试流程：
     * 1. 构造测试用户凭证JSON数据
     * 2. 发起POST请求到/user/login端点
     * 3. 验证响应状态码为200
     * 4. 验证响应内容为登录成功消息
     *
     * @throws Exception 测试过程中可能抛出的异常
     * @see UserController#login(User) 对应的控制器方法
     */
    @Test
    public void testLogin() throws Exception {
        String userJson = "{\"username\":\"test\",\"password\":\"pass\"}";

        String response = mockMvc.perform(post("/user/login")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // 验证响应是否包含JWT令牌
        // 原代码中缺少导入静态方法的语句，添加后即可使用assertNotNull方法
        assertNotNull(response);
        assertTrue(response.contains("token"));

        // 解析JWT令牌
        String token = new ObjectMapper().readTree(response).get("token").asText();
        assertNotNull(token);
        System.out.println("JWT Token: " + token);
        // 验证JWT令牌内容
        Claims claims = Jwts.parser()
                .setSigningKey("yourSecretKey")
                .parseClaimsJws(token)
                .getBody();
        assertEquals("test", claims.getSubject());

    
    }
}