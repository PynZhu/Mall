package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ProductMapper;
import com.mall.backend.model.entity.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> {
    // 分页查询商品
    public Page<Product> getProductPage(int pageNum, int pageSize) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        return page(page);
    }

    // 根据类目查询商品
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return baseMapper.selectByCategoryId(categoryId);
    }

    // 获取推荐商品
    public List<Product> getRecommendedProducts() {
        return baseMapper.selectRecommendedProducts();
    }

    // 根据关键词搜索商品
    public List<Product> searchProducts(String keyword) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        return list(queryWrapper);
    }

    // 添加商品
    public boolean addProduct(Product product) {
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        return save(product);
    }

    // 更新商品
    public boolean updateProductInfo(Product product) {
        product.setUpdateTime(LocalDateTime.now());
        return updateById(product);
    }
}