package com.mall.backend.utils;

public class SensitiveInfoUtils {
    // 手机号脱敏
    public static String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 11) {
            return phoneNumber;
        }
        return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    // 身份证脱敏
    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 15) {
            return idCard;
        }
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2");
    }

    // 邮箱脱敏
    public static String maskEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }
}