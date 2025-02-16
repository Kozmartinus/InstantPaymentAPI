package com.paymentapi.controller;

import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Payment;
import com.paymentapi.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendPayment(@RequestBody PaymentRequestDTO request) {
    paymentService.processPayment(request);
    return ResponseEntity.ok("Payment processed successfully");
  }

  @GetMapping("/history")
  public ResponseEntity<List<Payment>> getPaymentHistory() {
    return ResponseEntity.ok(paymentService.getAllPayments());
  }
}