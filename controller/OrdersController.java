package com.mall.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.OrderItem;
import com.mall.backend.model.entity.Orders;
import com.mall.backend.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    // 创建订单
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestParam Long userId,
            @RequestParam Long shippingAddressId,
            @RequestBody List<OrderItem> orderItems
    ) {
        Orders order = ordersService.createOrder(userId, orderItems, shippingAddressId);
        return ResponseEntity.ok(order);
    }

    // 支付订单
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payOrder(@PathVariable Long orderId) {
        boolean result = ordersService.payOrder(orderId);
        return result ?
                ResponseEntity.ok("支付成功") :
                ResponseEntity.badRequest().body("支付失败");
    }

    // 取消订单
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        boolean result = ordersService.cancelOrder(orderId);
        return result ?
                ResponseEntity.ok("取消成功") :
                ResponseEntity.badRequest().body("取消失败");
    }

    // 分页查询用户订单
    @GetMapping("/user")
    public ResponseEntity<Page<Orders>> getUserOrders(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Orders> orders = ordersService.getUserOrders(userId, pageNum, pageSize);
        return ResponseEntity.ok(orders);
    }
}