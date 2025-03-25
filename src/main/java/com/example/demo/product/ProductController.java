package com.example.demo.product;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.UserService; // 假设 UserService 类在 com.example.demo.user 包下，需根据实际情况调整

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductMapper productMapper;
// 为了解决 "UserService cannot be resolved to a type" 问题，需要导入 UserService 类。
private final UserService userService;

    public ProductController(ProductMapper productMapper, UserService userService) {
        this.productMapper = productMapper;
        this.userService = userService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }

    @PostMapping("/{productId}/purchase")
    public void purchaseProduct(@PathVariable Long productId, 
                              @RequestParam Integer quantity,
                              Authentication authentication) {
        userService.purchaseProduct(authentication.getName(), productId, quantity);
    }
}