package com.hardware.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequest {

    @NotNull(message = "Supplier is required")
    private Long supplierId;

    private String note;

    @Valid
    @NotEmpty(message = "At least one item is required")
    private List<PurchaseItemRequest> items;
}
