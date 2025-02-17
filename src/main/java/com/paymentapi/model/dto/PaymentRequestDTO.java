package com.paymentapi.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {

  @NotNull(message = "Sender ID cannot be null")
  private Long senderId;

  @NotNull(message = "Receiver ID cannot be null")
  private Long receiverId;

  @NotNull(message = "Amount cannot be null")
  @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
  private BigDecimal amount;

  @NotNull(message = "Idempotency key cannot be null")
  private String idempotencyKey;
}
