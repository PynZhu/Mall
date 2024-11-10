package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    // 根据角色ID查询权限
    List<Permission> selectPermissionsByRoleId(@Param("roleId") Long roleId);

    // 根据用户ID查询权限
    List<Permission> selectPermissionsByUserId(@Param("userId") Long userId);
}