package com.paymentapi.service;

import com.paymentapi.exception.DoubleSpendingException;
import com.paymentapi.exception.EntityNotFoundException;
import com.paymentapi.exception.InsufficientBalanceException;
import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Account;
import com.paymentapi.model.entity.Payment;
import com.paymentapi.repository.AccountRepository;
import com.paymentapi.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final AccountRepository accountRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public PaymentService(PaymentRepository paymentRepository, AccountRepository accountRepository, KafkaTemplate<String, String> kafkaTemplate) {
    this.paymentRepository = paymentRepository;
    this.accountRepository = accountRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Transactional
  public void processPayment(PaymentRequestDTO request) {
    Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());
    if (existingPayment.isPresent()) {
      throw new DoubleSpendingException("Payment has already been processed.");
    }

    Account sender = accountRepository.findByIdWithLock(request.getSenderId())
            .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
    Account receiver = accountRepository.findByIdWithLock(request.getReceiverId())
            .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

    if (sender.getBalance().compareTo(request.getAmount()) < 0) {
      throw new InsufficientBalanceException("Insufficient balance in sender account");
    }

    sender.setBalance(sender.getBalance().subtract(request.getAmount()));
    receiver.setBalance(receiver.getBalance().add(request.getAmount()));

    accountRepository.save(sender);
    accountRepository.save(receiver);

    Payment payment = new Payment();
    payment.setSenderId(request.getSenderId());
    payment.setReceiverId(request.getReceiverId());
    payment.setAmount(request.getAmount());
    payment.setIdempotencyKey(request.getIdempotencyKey());
    payment.setTimestamp(LocalDateTime.now());
    paymentRepository.save(payment);

    kafkaTemplate.send("payment-notifications", "Payment sent from " + request.getSenderId() + " to " + request.getReceiverId() + " of amount " + request.getAmount());
  }

  public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
  }
}