package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.UserCouponMapper;
import com.mall.backend.model.entity.UserCoupon;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserCouponService extends ServiceImpl<UserCouponMapper, UserCoupon> {

    // 获取用户优惠券
    public List<UserCoupon> getCouponsByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
}