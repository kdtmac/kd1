package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户REST API控制器
 * @RestController 组合了@Controller和@ResponseBody，用于创建RESTful web服务
 * @RequestMapping 定义类级别的请求路径基准为/user
 */
@RestController
@RequestMapping("/user")
public class UserController {

        /**
     * 通过@Autowired实现依赖注入
     * Spring会自动装配UserService接口的实现类
     */
    @Autowired
    private UserService userService;

        /**
     * 处理用户注册请求
     * @PostMapping 映射HTTP POST请求到/user/register路径
     */
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

        /**
     * 处理用户登录请求
     * @PostMapping 映射HTTP POST请求到/user/login路径
     */
    
    // 假设我们在这里定义LoginResponse类
    static class LoginResponse {
        private String token;

        public LoginResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody User user) {
        String token = userService.login(user);
        return new LoginResponse(token);
    }
}