package com.mall.backend.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.OrdersMapper;
import com.mall.backend.model.entity.OrderItem;
import com.mall.backend.model.entity.Orders;
import com.mall.backend.model.entity.Product;
import com.mall.backend.model.entity.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService extends ServiceImpl<OrdersMapper, Orders> {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSpecService productSpecService;

    // 创建订单
    @Transactional
    public Orders createOrder(Long userId, List<OrderItem> orderItems, Long shippingAddressId) {
        // 创建订单
        Orders order = new Orders();
        order.setUserId(userId);
        order.setOrderNo(generateOrderNo());
        order.setShippingAddressId(shippingAddressId);
        order.setStatus(0); // 待支付
        order.setCreateTime(LocalDateTime.now());

        // 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(orderItems);
        order.setTotalAmount(totalAmount);
        order.setActualAmount(totalAmount);

        // 保存订单
        save(order);

        // 保存订单项
        for (OrderItem item : orderItems) {
            item.setOrderId(order.getId());

            // 更新商品和规格库存
            updateProductStock(item);
        }
        orderItemService.saveBatch(orderItems);

        return order;
    }

    // 支付订单
    @Transactional
    public boolean payOrder(Long orderId) {
        Orders order = getById(orderId);
        if (order == null || order.getStatus() != 0) {
            return false;
        }

        order.setStatus(1); // 已支付
        order.setPaymentTime(LocalDateTime.now());
        return updateById(order);
    }

    // 取消订单
    @Transactional
    public boolean cancelOrder(Long orderId) {
        Orders order = getById(orderId);
        if (order == null || order.getStatus() != 0) {
            return false;
        }

        order.setStatus(4); // 已取消
        return updateById(order);
    }

    // 分页查询用户订单
    public Page<Orders> getUserOrders(Long userId, int pageNum, int pageSize) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return page(page, queryWrapper);
    }

    // 生成订单号
    private String generateOrderNo() {
        return IdUtil.getSnowflakeNextIdStr();
    }

    // 计算总金额
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 更新商品和规格库存
    private void updateProductStock(OrderItem item) {
        // 更新商品总库存
        Product product = productService.getById(item.getProductId());
        product.setStock(product.getStock() - item.getQuantity());
        productService.updateById(product);

        // 更新规格库存
        if (item.getProductSpecId() != null) {
            ProductSpec spec = productSpecService.getById(item.getProductSpecId());
            spec.setStock(spec.getStock() - item.getQuantity());
            productSpecService.updateById(spec);
        }
    }
}