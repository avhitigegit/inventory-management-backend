package com.hardware.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerPaymentRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal amount;

    private String note;
}
