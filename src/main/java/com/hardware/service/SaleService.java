package com.hardware.service;

import com.hardware.dto.request.SaleRequest;
import com.hardware.dto.response.SaleResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {

    SaleResponse createSale(SaleRequest request);

    SaleResponse getSaleById(Long id);

    List<SaleResponse> getSalesByDateRange(LocalDateTime start, LocalDateTime end);
}
