package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

    // 查询可用优惠券
    List<Coupon> selectAvailableCoupons();

    // 根据类型查询优惠券
    List<Coupon> selectCouponsByType(@Param("type") Integer type);
}