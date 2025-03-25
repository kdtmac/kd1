package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.example.demo.security.UserDetailsServiceImpl; // 假设 UserDetailsServiceImpl 在这个包路径下
// 若出现 'The import lombok cannot be resolved' 错误，需要在项目中添加 Lombok 依赖。
// 如果你使用的是 Maven 项目，在 pom.xml 中添加以下依赖：

// 如果你使用的是 Gradle 项目，在 build.gradle 中添加以下依赖：
// implementation 'org.projectlombok:lombok:1.18.26'
// annotationProcessor 'org.projectlombok:lombok:1.18.26'
import lombok.RequiredArgsConstructor;

@Component
// 由于 Lombok 的 @RequiredArgsConstructor 注解会自动生成一个包含所有 final 字段的构造函数，
// 而代码中已经手动定义了一个构造函数，导致出现重复定义的问题。
// 解决方法是移除手动定义的构造函数，让 Lombok 自动生成构造函数。
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
// 为了解决 'UserDetailsServiceImpl cannot be resolved to a type' 的问题，需要导入 UserDetailsServiceImpl 类。
    // @Autowired
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}