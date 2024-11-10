package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.PermissionMapper;
import com.mall.backend.model.entity.Permission;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    // 根据角色ID获取权限列表
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return baseMapper.selectPermissionsByRoleId(roleId);
    }

    // 根据用户ID获取权限列表
    public List<Permission> getUserPermissions(Long userId) {
        return baseMapper.selectPermissionsByUserId(userId);
    }

    // 检查用户权限
    public boolean checkUserPermission(Long userId, String permissionCode) {
        List<Permission> permissions = getUserPermissions(userId);
        return permissions.stream()
                .anyMatch(p -> p.getPermissionCode().equals(permissionCode));
    }
}