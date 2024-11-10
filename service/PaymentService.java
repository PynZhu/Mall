package com.mall.backend.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.enums.PaymentStatus;
import com.mall.backend.mapper.PaymentMapper;
import com.mall.backend.model.entity.Orders;
import com.mall.backend.model.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService extends ServiceImpl<PaymentMapper, Payment> {
    @Autowired
    private OrdersService ordersService;

    // 创建支付记录
    @Transactional
    public Payment createPayment(Long orderId, Integer paymentType) {
        Orders order = ordersService.getById(orderId);

        // 检查订单是否存在且未支付
        if (order == null || order.getStatus() != 0) {
            throw new RuntimeException("订单不存在或已支付");
        }

        // 创建支付记录
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentNo(generatePaymentNo());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentType(paymentType);
        payment.setStatus(PaymentStatus.UNPAID.getCode());
        payment.setCreateTime(LocalDateTime.now());

        // 保存支付记录
        save(payment);
        return payment;
    }

    // 模拟支付
    @Transactional
    public boolean processPayment(Long paymentId, String paymentChannel) {
        Payment payment = getById(paymentId);

        // 检查支付记录
        if (payment == null || payment.getStatus() != PaymentStatus.UNPAID.getCode()) {
            return false;
        }

        // 更新支付记录
        payment.setStatus(PaymentStatus.PAID.getCode());
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentChannel(paymentChannel);
        updateById(payment);

        // 更新订单状态
        Orders order = ordersService.getById(payment.getOrderId());
        order.setStatus(1); // 已支付
        order.setPaymentTime(LocalDateTime.now());
        ordersService.updateById(order);

        return true;
    }

    // 退款
    @Transactional
    public boolean refundPayment(Long paymentId) {
        Payment payment = getById(paymentId);

        // 检查支付记录
        if (payment == null || payment.getStatus() != PaymentStatus.PAID.getCode()) {
            return false;
        }

        // 更新支付记录
        payment.setStatus(PaymentStatus.REFUND.getCode());
        updateById(payment);

        // 更新订单状态
        Orders order = ordersService.getById(payment.getOrderId());
        order.setStatus(5); // 退款中
        ordersService.updateById(order);

        return true;
    }

    // 生成支付流水号
    private String generatePaymentNo() {
        return IdUtil.getSnowflakeNextIdStr();
    }

    // 查询支付状态
    public Payment getPaymentByOrderId(Long orderId) {
        return baseMapper.selectByOrderId(orderId);
    }
}