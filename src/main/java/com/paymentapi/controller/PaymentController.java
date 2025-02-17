package com.paymentapi.controller;

import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Payment;
import com.paymentapi.service.payment.PaymentManager;
import com.paymentapi.service.payment.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentManager paymentManager;
  private final PaymentService paymentService;

  public PaymentController(PaymentManager paymentManager, PaymentService paymentService) {
    this.paymentManager = paymentManager;
    this.paymentService = paymentService;
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendPayment(@Valid @RequestBody PaymentRequestDTO request) {
    paymentManager.processPayment(request);
    return ResponseEntity.ok("Payment processed successfully");
  }

  @GetMapping("/")
  public ResponseEntity<List<Payment>> getAllPayments() {
    return ResponseEntity.ok(paymentService.getAllPayments());
  }
}