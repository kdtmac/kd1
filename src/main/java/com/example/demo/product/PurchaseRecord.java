package com.example.demo.product;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class PurchaseRecord {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal amount;
    private LocalDateTime purchaseTime;

    // Getters and Setters
    public PurchaseRecord(String username, Long productId, Integer quantity){
        // Bug 修复：原构造函数没有 LocalDateTime 参数，且当前方法为构造函数，不能有返回值
        this.userId = null; // 假设 userId 后续再处理
        this.productId = productId;
        this.quantity = quantity;
        this.purchaseTime = LocalDateTime.now();
        // 假设 amount 后续根据商品单价和数量计算
        this.amount = BigDecimal.ZERO; 
        // 假设 id 由数据库自增或其他方式生成
        this.id = null;
    }
}