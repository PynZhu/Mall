package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ProductSpecMapper;
import com.mall.backend.model.entity.ProductSpec;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecService extends ServiceImpl<ProductSpecMapper, ProductSpec> {
    // 根据商品ID获取所有规格
    public List<ProductSpec> getProductSpecs(Long productId) {
        return baseMapper.selectByProductId(productId);
    }

    // 添加商品规格
    public boolean addProductSpec(ProductSpec spec) {
        return save(spec);
    }

    // 批量添加商品规格
    public boolean addProductSpecs(List<ProductSpec> specs) {
        return saveBatch(specs);
    }

    // 更新商品规格库存
    public boolean updateSpecStock(Long specId, Integer stock) {
        ProductSpec spec = getById(specId);
        if (spec != null) {
            spec.setStock(stock);
            return updateById(spec);
        }
        return false;
    }

    // 检查规格是否有库存
    public boolean checkSpecStock(Long specId) {
        ProductSpec spec = getById(specId);
        return spec != null && spec.getStock() > 0;
    }
}