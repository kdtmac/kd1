package com.example.demo.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


import com.example.demo.product.Product;
@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM products")
    List<Product> findAll();

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product findById(Long id);

    // 添加返回类型 int 表示受影响的行数
    @Update("UPDATE products SET stock = #{stock} WHERE id = #{id}")
    int updateStock(Long id, int stock);
  
}//修改后使用 gtIteByI 方法获取商品信息
