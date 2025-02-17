package com.paymentapi.service.account;

import com.paymentapi.exception.EntityNotFoundException;
import com.paymentapi.exception.PaymentException;
import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Account;
import com.paymentapi.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void handleBalances(PaymentRequestDTO request) {
        Account sender = accountRepository.findByIdWithLock(request.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
        Account receiver = accountRepository.findByIdWithLock(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new PaymentException("Insufficient balance in sender account");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
