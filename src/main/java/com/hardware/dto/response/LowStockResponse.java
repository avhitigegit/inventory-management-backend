package com.hardware.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LowStockResponse {

    private Long productId;
    private String productName;
    private String sku;
    private Integer quantity;
    private Integer reorderLevel;
}
