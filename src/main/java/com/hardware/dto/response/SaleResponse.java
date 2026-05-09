package com.hardware.dto.response;

import com.hardware.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private List<SaleItemResponse> items;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal paidAmount;
    private PaymentType paymentType;
    private LocalDateTime createdAt;
}
