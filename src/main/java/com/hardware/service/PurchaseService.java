package com.hardware.service;

import com.hardware.dto.request.PurchaseRequest;
import com.hardware.dto.response.PurchaseResponse;

import java.util.List;

public interface PurchaseService {

    PurchaseResponse createPurchase(PurchaseRequest request);

    PurchaseResponse getPurchaseById(Long id);

    List<PurchaseResponse> getAllPurchases();
}
