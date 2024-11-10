package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    // 批量插入方法
    @Insert("<script>" +
            "INSERT INTO role_permission (role_id, permission_id) VALUES " +
            "<foreach collection='rolePermissions' item='item' separator=','>" +
            "(#{item.roleId}, #{item.permissionId})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("rolePermissions") List<RolePermission> rolePermissions);
}