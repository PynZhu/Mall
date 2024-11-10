package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.UserAddressMapper;
import com.mall.backend.model.entity.UserAddress;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressService extends ServiceImpl<UserAddressMapper, UserAddress> {
    public List<UserAddress> getAddressesByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }

    public boolean addAddress(UserAddress address) {
        return save(address);
    }
}