package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.Product;
import com.mall.backend.model.dto.ProductSalesRankingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    // 根据类目查询商品
    List<Product> selectByCategoryId(@Param("categoryId") Long categoryId);

    // 查询推荐商品
    List<Product> selectRecommendedProducts();

    // 获取热销商品排行
    @Select("SELECT p.id AS productId, p.name AS productName, " +
            "SUM(oi.quantity) AS salesCount, " +
            "SUM(oi.quantity * oi.price) AS salesAmount " +
            "FROM product p " +
            "JOIN order_item oi ON p.id = oi.product_id " +
            "JOIN orders o ON oi.order_id = o.id " +
            "WHERE o.status = 1 " +
            "GROUP BY p.id, p.name " +
            "ORDER BY salesCount DESC " +
            "LIMIT 10")
    List<ProductSalesRankingDTO> selectProductSalesRanking();
}