package com.paymentapi.exception;

public class DoubleSpendingException extends RuntimeException {
    public DoubleSpendingException(String message) {
        super(message);
    }
}
