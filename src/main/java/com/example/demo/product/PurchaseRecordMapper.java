package com.example.demo.product;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PurchaseRecordMapper {
    @Insert("INSERT INTO purchase_records(user_id, product_id, quantity, amount, purchase_time) " +
            "VALUES(#{userId}, #{productId}, #{quantity}, #{amount}, #{purchaseTime})")
    void insert(PurchaseRecord record);
}