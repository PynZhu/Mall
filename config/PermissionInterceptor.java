package com.mall.backend.config;

import com.mall.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从token或session获取用户ID
        Long userId = getCurrentUserId();

        // 获取请求的URL和方法
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();

        // 检查用户是否有权限
        boolean hasPermission = permissionService.checkUserPermission(userId, generatePermissionCode(requestUrl, method));

        if (!hasPermission) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权限访问");
            return false;
        }

        return true;
    }

    private String generatePermissionCode(String url, String method) {
        return method + ":" + url;
    }

    private Long getCurrentUserId() {
        // 从token或session获取用户ID的逻辑
        return null;
    }
}