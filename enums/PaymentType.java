package com.mall.backend.enums;

public enum PaymentType {
    WECHAT(0, "微信支付"),
    ALIPAY(1, "支付宝"),
    BALANCE(2, "余额支付");

    private final int code;
    private final String description;

    PaymentType(int code, String description) {
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