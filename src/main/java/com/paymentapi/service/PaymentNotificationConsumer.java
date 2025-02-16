package com.paymentapi.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentNotificationConsumer {

  @KafkaListener(topics = "payment-notifications", groupId = "payment-group")
  public void consume(String message) {
    System.out.println("Received payment notification: " + message);
    // Further processing (e.g., sending an email or updating a UI)
  }
}