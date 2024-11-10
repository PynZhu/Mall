package com.mall.backend.enums;

public enum PaymentStatus {
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    REFUND(2, "已退款"),
    CLOSED(3, "已关闭");

    private final int code;
    private final String description;

    PaymentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

