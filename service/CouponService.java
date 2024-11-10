package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.CouponMapper;
import com.mall.backend.mapper.UserCouponMapper;
import com.mall.backend.model.entity.Coupon;
import com.mall.backend.model.entity.UserCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService extends ServiceImpl<CouponMapper, Coupon> {
    @Autowired
    private UserCouponMapper userCouponMapper;

    // 创建优惠券
    public boolean createCoupon(Coupon coupon) {
        coupon.setUsedCount(0);
        coupon.setStatus(1);  // 启用
        return save(coupon);
    }

    // 查询可用优惠券
    public List<Coupon> getAvailableCoupons() {
        return baseMapper.selectAvailableCoupons();
    }

    // 发放优惠券
    @Transactional
    public boolean distributeCoupon(Long userId, Long couponId) {
        Coupon coupon = getById(couponId);

        // 检查优惠券是否可用
        if (coupon == null || coupon.getStatus() != 1 ||
                coupon.getTotalCount() <= coupon.getUsedCount()) {
            return false;
        }

        // 创建用户优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus(0);  // 未使用
        userCoupon.setReceiveTime(LocalDateTime.now());

        // 更新优惠券已使用数量
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        updateById(coupon);

        return userCouponMapper.insert(userCoupon) > 0;
    }

    // 使用优惠券
    @Transactional
    public boolean useCoupon(Long userCouponId, BigDecimal orderAmount) {
        UserCoupon userCoupon = userCouponMapper.selectById(userCouponId);
        Coupon coupon = getById(userCoupon.getCouponId());

        // 检查优惠券是否可用
        if (userCoupon == null || userCoupon.getStatus() != 0 ||
                coupon == null || coupon.getMinAmount().compareTo(orderAmount) > 0) {
            return false;
        }

        // 更新用户优惠券状态
        userCoupon.setStatus(1);  // 已使用
        userCoupon.setUseTime(LocalDateTime.now());
        userCouponMapper.updateById(userCoupon);

        return true;
    }

    // 获取用户可用优惠券
    public List<UserCoupon> getUserAvailableCoupons(Long userId) {
        return userCouponMapper.selectAvailableUserCoupons(userId);
    }
}