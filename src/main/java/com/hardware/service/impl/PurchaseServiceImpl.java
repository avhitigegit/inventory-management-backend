package com.hardware.service.impl;

import com.hardware.dto.request.PurchaseItemRequest;
import com.hardware.dto.request.PurchaseRequest;
import com.hardware.dto.response.PurchaseItemResponse;
import com.hardware.dto.response.PurchaseResponse;
import com.hardware.entity.Product;
import com.hardware.entity.Purchase;
import com.hardware.entity.PurchaseItem;
import com.hardware.entity.Supplier;
import com.hardware.exception.ResourceNotFoundException;
import com.hardware.repository.ProductRepository;
import com.hardware.repository.PurchaseRepository;
import com.hardware.repository.SupplierRepository;
import com.hardware.service.ProductService;
import com.hardware.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", request.getSupplierId()));

        List<PurchaseItem> purchaseItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.getProductId()));

            BigDecimal itemTotal = itemRequest.getCostPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            purchaseItems.add(PurchaseItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .costPrice(itemRequest.getCostPrice())
                    .totalPrice(itemTotal)
                    .build());
        }

        Purchase purchase = Purchase.builder()
                .supplier(supplier)
                .totalAmount(totalAmount)
                .note(request.getNote())
                .build();

        for (PurchaseItem item : purchaseItems) {
            item.setPurchase(purchase);
            purchase.getItems().add(item);
        }

        Purchase savedPurchase = purchaseRepository.save(purchase);

        for (PurchaseItemRequest itemRequest : request.getItems()) {
            productService.updateStock(itemRequest.getProductId(), itemRequest.getQuantity());
        }

        return toResponse(savedPurchase);
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseResponse getPurchaseById(Long id) {
        return toResponse(purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponse> getAllPurchases() {
        return purchaseRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private PurchaseResponse toResponse(Purchase purchase) {
        List<PurchaseItemResponse> items = purchase.getItems().stream()
                .map(item -> PurchaseItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .costPrice(item.getCostPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        return PurchaseResponse.builder()
                .id(purchase.getId())
                .supplierId(purchase.getSupplier().getId())
                .supplierName(purchase.getSupplier().getName())
                .items(items)
                .totalAmount(purchase.getTotalAmount())
                .note(purchase.getNote())
                .createdAt(purchase.getCreatedAt())
                .build();
    }
}
