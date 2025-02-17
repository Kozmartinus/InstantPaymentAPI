package com.paymentapi.service.payment;

import com.paymentapi.exception.PaymentException;
import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Payment;
import com.paymentapi.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void createPayment(PaymentRequestDTO request) {
        if (request.getSenderId().equals(request.getReceiverId())) {
            throw new PaymentException("Sender and receiver cannot be the same.");
        }
        Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());
        if (existingPayment.isPresent()) {
            throw new PaymentException("Payment has already been processed.");
        }
        Payment payment = new Payment();
        payment.setSenderId(request.getSenderId());
        payment.setReceiverId(request.getReceiverId());
        payment.setAmount(request.getAmount());
        payment.setIdempotencyKey(request.getIdempotencyKey());
        payment.setTimestamp(LocalDateTime.now());
        try {
            paymentRepository.save(payment);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage(), ex);
            throw new PaymentException("Error during payment save.");
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}