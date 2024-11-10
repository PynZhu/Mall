package com.mall.backend.model.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AdminDashboardDTO {
    // 总销售额
    private BigDecimal totalSales;

    // 订单数量
    private Integer totalOrderCount;

    // 商品总数
    private Integer totalProductCount;

    // 用户总数
    private Integer totalUserCount;

    // 热销商品排行
    private List<ProductSalesRankingDTO> productSalesRanking;

    // 最近7天销售趋势
    private List<SalesTrendDTO> salesTrend;
}