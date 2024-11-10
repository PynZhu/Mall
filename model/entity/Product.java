package com.mall.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal marketPrice;
    private Integer stock;
    private Long categoryId;
    private Long sellerId;
    private String imageUrls;
    private Integer status;
    private Integer isRecommended;
    private Integer saleCount;
    private Integer commentCount;
    private BigDecimal avgRating;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}