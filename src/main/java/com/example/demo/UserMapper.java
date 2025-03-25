package com.example.demo;

import com.example.demo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<User> selectByUsername(String username);

    @Insert("INSERT INTO users(username, password) VALUES(#{username}, #{password})")
    void insertUser(User user);
}