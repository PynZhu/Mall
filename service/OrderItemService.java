package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.OrderItemMapper;
import com.mall.backend.model.entity.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItem> {
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return baseMapper.selectByOrderId(orderId);
    }

    public boolean addOrderItem(OrderItem orderItem) {
        return save(orderItem);
    }
}