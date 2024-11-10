package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ProductCategoryMapper;
import com.mall.backend.model.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService extends ServiceImpl<ProductCategoryMapper, ProductCategory> {

    // 获取所有一级类目
    public List<ProductCategory> getTopLevelCategories() {
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("parent_id").or().eq("parent_id", 0);
        return list(queryWrapper);
    }

    // 获取子类目
    public List<ProductCategory> getChildCategories(Long parentId) {
        if (parentId == null) {
            return getTopLevelCategories();
        }

        return baseMapper.selectByParentId(parentId);
    }
}