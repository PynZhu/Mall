package com.mall.backend.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductSalesRankingDTO {
    private Long productId;
    private String productName;
    private Integer salesCount;
    private BigDecimal salesAmount;
}