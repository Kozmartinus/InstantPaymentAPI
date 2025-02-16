package com.paymentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DoubleSpendingException extends RuntimeException {
    public DoubleSpendingException(String message) {
        super(message);
    }
}
