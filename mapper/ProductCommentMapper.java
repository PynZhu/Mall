package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.ProductComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductCommentMapper extends BaseMapper<ProductComment> {
    // 根据商品ID查询评论
    List<ProductComment> selectByProductId(@Param("productId") Long productId);

    // 查询商品平均评分
    Double calculateAverageRating(@Param("productId") Long productId);
}