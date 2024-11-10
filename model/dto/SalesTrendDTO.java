package com.mall.backend.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SalesTrendDTO {
    private String date;
    private BigDecimal salesAmount;
}