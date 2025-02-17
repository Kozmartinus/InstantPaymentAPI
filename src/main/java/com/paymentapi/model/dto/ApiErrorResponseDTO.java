package com.paymentapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiErrorResponseDTO {
    private final String title;
    private final int status;
    private final String detail;
    private final String instance;
    private final LocalDateTime dateTime;
}