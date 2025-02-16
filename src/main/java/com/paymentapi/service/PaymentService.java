package com.paymentapi.service;

import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Payment;
import com.paymentapi.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public PaymentService(PaymentRepository paymentRepository, KafkaTemplate<String, String> kafkaTemplate) {
    this.paymentRepository = paymentRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Transactional
  public void processPayment(PaymentRequestDTO request) {
    Payment payment = new Payment(request.getSenderId(), request.getReceiverId(), request.getAmount());
    paymentRepository.save(payment);
    kafkaTemplate.send("payment-notifications", "Payment sent from " + request.getSenderId() + " to " + request.getReceiverId() + " of amount " + request.getAmount());
  }

  public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
  }
}