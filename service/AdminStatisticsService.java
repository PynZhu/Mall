package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.backend.mapper.OrdersMapper;
import com.mall.backend.mapper.ProductMapper;
import com.mall.backend.mapper.UserMapper;
import com.mall.backend.model.dto.AdminDashboardDTO;
import com.mall.backend.model.dto.ProductSalesRankingDTO;
import com.mall.backend.model.dto.SalesTrendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminStatisticsService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    public AdminDashboardDTO getDashboardStatistics() {
        AdminDashboardDTO dashboard = new AdminDashboardDTO();

        // 计算总销售额
        dashboard.setTotalSales(calculateTotalSales());

        // 计算订单数量
        dashboard.setTotalOrderCount(countTotalOrders());

        // 计算商品总数
        dashboard.setTotalProductCount(countTotalProducts());

        // 计算用户总数
        dashboard.setTotalUserCount(countTotalUsers());

        // 获取热销商品排行
        dashboard.setProductSalesRanking(getProductSalesRanking());

        // 获取最近7天销售趋势
        dashboard.setSalesTrend(getSalesTrend());

        return dashboard;
    }

    private BigDecimal calculateTotalSales() {
        // 使用自定义查询计算总销售额
        return ordersMapper.selectTotalSales();
    }

    private Integer countTotalOrders() {
        return ordersMapper.selectCount(null).intValue();
    }

    private Integer countTotalProducts() {
        return productMapper.selectCount(null).intValue();
    }

    private Integer countTotalUsers() {
        return userMapper.selectCount(null).intValue();
    }

    private List<ProductSalesRankingDTO> getProductSalesRanking() {
        // 查询热销商品排行
        return productMapper.selectProductSalesRanking();
    }

    private List<SalesTrendDTO> getSalesTrend() {
        // 获取最近7天的销售趋势
        return ordersMapper.selectSalesTrendLastSevenDays();
    }
}