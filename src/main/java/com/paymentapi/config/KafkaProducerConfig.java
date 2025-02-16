package com.paymentapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

  @Bean
  public NewTopic paymentTopic() {
    return new NewTopic("payment-notifications", 1, (short) 1);
  }
}
