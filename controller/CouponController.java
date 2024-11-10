package com.mall.backend.controller;

import com.mall.backend.model.entity.Coupon;
import com.mall.backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    // 获取所有可用优惠券
    @GetMapping("/available")
    public ResponseEntity<List<Coupon>> getAvailableCoupons() {
        List<Coupon> coupons = couponService.getAvailableCoupons();
        return ResponseEntity.ok(coupons);
    }

    // 根据类型获取优惠券
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Coupon>> getCouponsByType(@PathVariable Integer type) {
        List<Coupon> coupons = couponService.getCouponsByType(type);
        return ResponseEntity.ok(coupons);
    }

    // 创建优惠券
    @PostMapping("/create")
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        boolean result = couponService.createCoupon(coupon);
        return result ?
                ResponseEntity.ok("优惠券创建成功") :
                ResponseEntity.badRequest().body("优惠券创建失败");
    }

    // 发放优惠券
    @PostMapping("/distribute")
    public ResponseEntity<?> distributeCoupon(
            @RequestParam Long userId,
            @RequestParam Long couponId
    ) {
        boolean result = couponService.distributeCoupon(userId, couponId);
        return result ?
                ResponseEntity.ok("优惠券发放成功") :
                ResponseEntity.badRequest().body("优惠券发放失败");
    }
}