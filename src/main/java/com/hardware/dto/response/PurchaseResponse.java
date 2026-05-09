package com.hardware.dto.response;

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
public class PurchaseResponse {

    private Long id;
    private Long supplierId;
    private String supplierName;
    private List<PurchaseItemResponse> items;
    private BigDecimal totalAmount;
    private String note;
    private LocalDateTime createdAt;
}
