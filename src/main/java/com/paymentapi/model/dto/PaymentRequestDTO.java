package com.paymentapi.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDTO {
  private Long senderId;
  private Long receiverId;
  private BigDecimal amount;
}
