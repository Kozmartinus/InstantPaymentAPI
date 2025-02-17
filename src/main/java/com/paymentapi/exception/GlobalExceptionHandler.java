package com.paymentapi.exception;

import com.paymentapi.model.dto.ApiErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(PaymentException.class)
    public ApiErrorResponseDTO handlePaymentException(PaymentException ex, HttpServletRequest request) {
        return new ApiErrorResponseDTO(
                "Error during payment",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiErrorResponseDTO handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return new ApiErrorResponseDTO(
                "Entity not found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiErrorResponseDTO handleUnexpectedError(RuntimeException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        return new ApiErrorResponseDTO(
                "Unexpected error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please contact support.",
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }
}
