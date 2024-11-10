package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {
    // 查询用户可用的优惠券
    List<UserCoupon> selectAvailableUserCoupons(@Param("userId") Long userId);
}