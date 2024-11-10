package com.mall.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Orders {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal actualAmount;
    private Integer status;
    private Integer paymentType;
    private Long shippingAddressId;
    private Integer shippingMethod;
    private BigDecimal shippingFee;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime paymentTime;
    private LocalDateTime shippingTime;
    private LocalDateTime completeTime;
}