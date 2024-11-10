package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ProductCommentMapper;
import com.mall.backend.model.entity.ProductComment;
import com.mall.backend.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Service
public class ProductCommentService extends ServiceImpl<ProductCommentMapper, ProductComment> {
    @Autowired
    private ProductService productService;

    // 添加评论
    @Transactional
    public boolean addComment(ProductComment comment) {
        // 设置创建时间
        comment.setCreateTime(LocalDateTime.now());

        // 保存评论
        boolean result = save(comment);

        // 更新商品的评论数和平均评分
        if (result) {
            updateProductCommentStats(comment.getProductId());
        }

        return result;
    }

    // 分页查询商品评论
    public Page<ProductComment> getProductComments(Long productId, int pageNum, int pageSize) {
        Page<ProductComment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ProductComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId)
                .orderByDesc("create_time");
        return page(page, queryWrapper);
    }

    // 更新商品评论统计
    private void updateProductCommentStats(Long productId) {
        // 查询平均评分
        Double avgRating = baseMapper.calculateAverageRating(productId);

        // 查询评论数
        int commentCount = (int) count(new QueryWrapper<ProductComment>()
    .eq("product_id", productId));
        // 更新商品信息
        Product product = productService.getById(productId);
        product.setAvgRating(avgRating != null ? BigDecimal.valueOf(avgRating) : BigDecimal.ZERO);
        product.setCommentCount(commentCount);
        productService.updateById(product);
    }

    // 获取用户的评论历史
    public List<ProductComment> getUserComments(Long userId) {
        QueryWrapper<ProductComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .orderByDesc("create_time");
        return list(queryWrapper);
    }
}