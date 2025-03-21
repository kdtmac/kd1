package com.example.demo.product;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;

    // Getters and Setters
    public Integer getStock() {
        return stock;
    }
}