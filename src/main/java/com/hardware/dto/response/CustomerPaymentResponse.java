package com.hardware.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPaymentResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private BigDecimal amount;
    private String note;
    private BigDecimal remainingBalance;
    private LocalDateTime createdAt;
}
