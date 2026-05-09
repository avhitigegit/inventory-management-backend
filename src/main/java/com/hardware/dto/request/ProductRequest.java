package com.hardware.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String sku;
    private String barcode;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private Long supplierId;

    private String brand;
    private String unit;

    @NotNull(message = "Cost price is required")
    @PositiveOrZero(message = "Cost price must be zero or positive")
    private BigDecimal costPrice;

    @NotNull(message = "Selling price is required")
    @PositiveOrZero(message = "Selling price must be zero or positive")
    private BigDecimal sellingPrice;

    @PositiveOrZero(message = "Reorder level must be zero or positive")
    private Integer reorderLevel = 0;
}
