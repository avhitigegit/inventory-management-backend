package com.hardware.service;

import com.hardware.dto.request.StockAdjustmentRequest;
import com.hardware.dto.response.StockAdjustmentResponse;

import java.util.List;

public interface StockAdjustmentService {

    StockAdjustmentResponse createAdjustment(StockAdjustmentRequest request);

    List<StockAdjustmentResponse> getAllAdjustments();
}
