package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.ProductSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {
    // 根据商品ID查询规格
    List<ProductSpec> selectByProductId(@Param("productId") Long productId);
}