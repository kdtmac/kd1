package com.example.demo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface UserService {
    String register(User user);
    String login(User user);
}

/**
 * 服务层实现类，处理用户注册和登录业务逻辑
 * @Service 注解表示该类是Spring框架管理的服务层组件
 */
@Service
class UserServiceImpl implements UserService {
// 为了使用 @Autowired 注解，需要引入 Spring 的依赖
// 原来的 @Autowired 注解
    /**
     * 使用@Autowired实现依赖注入
     * Spring会自动装配UserMapper接口的实现（MyBatis生成代理对象）
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * Spring Security的密码编码器
     * @Autowired 根据类型自动注入配置的PasswordEncoder bean
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(User user) {
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
        return "User registered successfully";
    }

    @Override
    public String login(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null || !passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return "Invalid username or password";
        }
        return "Login successful";
    }
}