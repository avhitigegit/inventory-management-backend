package com.hardware.dto.request;

import com.hardware.enums.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SaleRequest {

    private Long customerId;

    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;

    @PositiveOrZero(message = "Discount must be zero or positive")
    private BigDecimal discount = BigDecimal.ZERO;

    @NotNull(message = "Paid amount is required")
    @PositiveOrZero(message = "Paid amount must be zero or positive")
    private BigDecimal paidAmount;

    @Valid
    @NotEmpty(message = "At least one item is required")
    private List<SaleItemRequest> items;
}
