package com.mall.backend.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

public class IdempotencyInterceptor implements HandlerInterceptor {
    private ConcurrentHashMap<String, Boolean> requestMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Idempotency-Key");

        if (token == null) {
            return true;
        }

        if (requestMap.containsKey(token)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "重复请求");
            return false;
        }

        requestMap.put(token, true);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String token = request.getHeader("Idempotency-Key");

        if (token != null) {
            requestMap.remove(token);
        }
    }
}