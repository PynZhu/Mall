package com.mall.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("user_coupon")
public class UserCoupon {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;  // 0-未使用，1-已使用，2-已过期
    private LocalDateTime receiveTime;
    private LocalDateTime useTime;

    // 额外的优惠券信息（非数据库字段）
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private Integer discountType;
    @TableField(exist = false)
    private BigDecimal discountValue;
    @TableField(exist = false)
    private BigDecimal minAmount;
}