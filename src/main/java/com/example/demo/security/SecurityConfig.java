package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
// 假设 jwtAuthFilter 来自 com.example.demo.security.filter 包
import com.example.demo.security.JwtAuthFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
@Configuration
@EnableWebSecurity
// 从 Spring Security 5.7 开始，WebSecurityConfigurerAdapter 已被弃用，建议使用基于组件的配置方式
// 这里使用 SecurityFilterChain 来替代 WebSecurityConfigurerAdapter
// 移除 extends WebSecurityConfigurerAdapter
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(auth -> auth
                .antMatchers("/user/register", "/user/login").permitAll()
                .antMatchers("/products", "/purchase").authenticated()
                .anyRequest().authenticated())
            .csrf(csrf -> csrf.disable())
            // 添加 JwtAuthFilter 到过滤器链中，确保在 UsernamePasswordAuthenticationFilter 之前执行
// 报错可能是因为 jwtAuthFilter() 方法未定义。需要确保在 SecurityConfig 类中定义了这个方法。
// 假设已经有一个 JwtAuthFilter 类，并且希望将其作为一个 Bean 注入到过滤器链中，需要添加一个生成 JwtAuthFilter Bean 的方法。
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // 添加生成 JwtAuthFilter Bean 的方法
    private JwtAuthFilter jwtAuthFilter;
// 引入 Autowired 注解
    @Autowired
    public void setJwtAuthFilter(JwtAuthFilter jwtAuthFilter) {
        // 声明一个实例变量来存储 JwtAuthFilter
        
        this.jwtAuthFilter = jwtAuthFilter;
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}