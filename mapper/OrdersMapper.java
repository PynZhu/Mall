package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.Orders;
import com.mall.backend.model.dto.SalesTrendDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    // 根据用户ID查询订单
    List<Orders> selectByUserId(@Param("userId") Long userId);

    // 根据订单状态查询订单
    List<Orders> selectByStatus(@Param("status") Integer status);

    // 计算总销售额
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE status = 1")
    BigDecimal selectTotalSales();

    // 获取最近7天销售趋势
    @Select("SELECT DATE(create_time) AS date, SUM(total_amount) AS sales_amount " +
            "FROM orders " +
            "WHERE create_time >= DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) " +
            "AND status = 1 " +
            "GROUP BY DATE(create_time)")
    List<SalesTrendDTO> selectSalesTrendLastSevenDays();
}