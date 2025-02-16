package com.paymentapi.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
  private Long senderId;
  private Long receiverId;
  private BigDecimal amount;
  private String idempotencyKey;
}
