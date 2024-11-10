package com.mall.backend.service;

import com.mall.backend.model.entity.Orders;
import com.mall.backend.model.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrdersServiceTest {
    @Autowired
    private OrdersService ordersService;

    @Test
    public void testCreateOrder() {
        // 准备测试数据
        Long userId = 1L; // 假设存在的用户ID
        Long shippingAddressId = 1L; // 假设存在的地址ID

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(100));
        orderItems.add(item);

        // 创建订单
        Orders order = ordersService.createOrder(userId, orderItems, shippingAddressId);

        // 验证
        assertNotNull(order);
        assertEquals(userId, order.getUserId());
        assertEquals(BigDecimal.valueOf(200), order.getTotalAmount());
    }

    @Test
    public void testCancelOrder() {
        // 创建一个订单
        Long userId = 1L;
        Long shippingAddressId = 1L;

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setProductId(1L);
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(100));
        orderItems.add(item);

        Orders order = ordersService.createOrder(userId, orderItems, shippingAddressId);

        // 取消订单
        boolean result = ordersService.cancelOrder(order.getId());

        // 验证
        assertTrue(result);
        Orders canceledOrder = ordersService.getById(order.getId());
        assertEquals(4, canceledOrder.getStatus()); // 已取消状态
    }
}