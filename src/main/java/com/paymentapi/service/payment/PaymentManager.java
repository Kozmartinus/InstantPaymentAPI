package com.paymentapi.service.payment;

import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.service.account.AccountService;
import com.paymentapi.service.notification.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentManager {

    private final PaymentService paymentService;
    private final AccountService accountService;
    private final NotificationService notificationService;

    public PaymentManager(PaymentService paymentService, AccountService accountService, NotificationService notificationService) {
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.notificationService = notificationService;
    }

    @Transactional
    public void processPayment(PaymentRequestDTO request) {
        paymentService.createPayment(request);
        accountService.handleBalances(request);
        notificationService.publishMessage("payment-notifications", "Payment sent from " + request.getSenderId() + " to " + request.getReceiverId() + " of amount " + request.getAmount());
    }
}
