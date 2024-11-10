package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    List<ProductCategory> selectByParentId(@Param("parentId") Long parentId);
}