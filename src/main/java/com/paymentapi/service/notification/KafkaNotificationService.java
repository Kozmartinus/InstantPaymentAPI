package com.paymentapi.service.notification;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationService implements NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaNotificationService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
