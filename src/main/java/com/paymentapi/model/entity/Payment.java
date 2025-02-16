package com.paymentapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long senderId;
  private Long receiverId;
  private BigDecimal amount;

  public Payment(Long senderId, Long receiverId, BigDecimal amount) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.amount = amount;
  }
}

