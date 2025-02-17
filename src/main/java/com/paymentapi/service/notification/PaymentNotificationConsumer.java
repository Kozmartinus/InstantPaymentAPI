package com.paymentapi.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentNotificationConsumer {

  @KafkaListener(topics = "payment-notifications", groupId = "payment-group")
  public void consume(String message) {
    log.info("Received payment notification: " + message);
  }
}