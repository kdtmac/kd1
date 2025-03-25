package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.UserMapper; // 假设 UserMapper 在这个包路径下，根据实际情况修改

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

// 为了解决 UserMapper 无法解析为类型的问题，需要引入 UserMapper 类。
private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.selectByUsername(username)
                // 假设 userMapper.selectByUsername(username) 返回的是一个 Optional<User>
                // 确保 userMapper.selectByUsername(username) 返回的是一个 Optional 对象
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}