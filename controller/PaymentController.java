package com.mall.backend.controller;

import com.mall.backend.model.entity.Payment;
import com.mall.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    // 创建支付记录
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(
            @RequestParam Long orderId,
            @RequestParam Integer paymentType
    ) {
        try {
            Payment payment = paymentService.createPayment(orderId, paymentType);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 模拟支付
    @PostMapping("/process")
    public ResponseEntity<?> processPayment(
            @RequestParam Long paymentId,
            @RequestParam String paymentChannel
    ) {
        boolean result = paymentService.processPayment(paymentId, paymentChannel);
        return result ?
                ResponseEntity.ok("支付成功") :
                ResponseEntity.badRequest().body("支付失败");
    }

    // 退款
    @PostMapping("/refund")
    public ResponseEntity<?> refundPayment(@RequestParam Long paymentId) {
        boolean result = paymentService.refundPayment(paymentId);
        return result ?
                ResponseEntity.ok("退款成功") :
                ResponseEntity.badRequest().body("退款失败");
    }

    // 查询支付状态
    @GetMapping("/status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable Long orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return payment != null ?
                ResponseEntity.ok(payment) :
                ResponseEntity.notFound().build();
    }
}