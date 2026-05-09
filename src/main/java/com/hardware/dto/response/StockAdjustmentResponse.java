package com.hardware.dto.response;

import com.hardware.enums.AdjustmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustmentResponse {

    private Long id;
    private Long productId;
    private String productName;
    private AdjustmentType adjustmentType;
    private Integer quantity;
    private String reason;
    private LocalDateTime createdAt;
}
