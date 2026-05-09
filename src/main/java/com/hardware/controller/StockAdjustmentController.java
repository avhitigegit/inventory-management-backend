package com.hardware.controller;

import com.hardware.dto.request.StockAdjustmentRequest;
import com.hardware.dto.response.StockAdjustmentResponse;
import com.hardware.service.StockAdjustmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-adjustments")
@RequiredArgsConstructor
public class StockAdjustmentController {

    private final StockAdjustmentService stockAdjustmentService;

    @GetMapping
    public ResponseEntity<List<StockAdjustmentResponse>> getAllAdjustments() {
        return ResponseEntity.ok(stockAdjustmentService.getAllAdjustments());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'STORE_KEEPER')")
    public ResponseEntity<StockAdjustmentResponse> createAdjustment(
            @Valid @RequestBody StockAdjustmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockAdjustmentService.createAdjustment(request));
    }
}
