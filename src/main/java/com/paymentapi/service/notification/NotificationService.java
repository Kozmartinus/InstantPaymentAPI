package com.paymentapi.service.notification;

public interface NotificationService {

    void publishMessage(String topic, String message);
}
