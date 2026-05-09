package com.hardware.service.impl;

import com.hardware.dto.request.StockAdjustmentRequest;
import com.hardware.dto.response.StockAdjustmentResponse;
import com.hardware.entity.Product;
import com.hardware.entity.StockAdjustment;
import com.hardware.enums.AdjustmentType;
import com.hardware.exception.InsufficientStockException;
import com.hardware.exception.ResourceNotFoundException;
import com.hardware.repository.ProductRepository;
import com.hardware.repository.StockAdjustmentRepository;
import com.hardware.service.StockAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockAdjustmentServiceImpl implements StockAdjustmentService {

    private final StockAdjustmentRepository stockAdjustmentRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public StockAdjustmentResponse createAdjustment(StockAdjustmentRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        if (request.getAdjustmentType() == AdjustmentType.DECREASE) {
            if (product.getQuantity() < request.getQuantity()) {
                throw new InsufficientStockException(
                        product.getName(), request.getQuantity(), product.getQuantity());
            }
            product.setQuantity(product.getQuantity() - request.getQuantity());
        } else {
            product.setQuantity(product.getQuantity() + request.getQuantity());
        }
        productRepository.save(product);

        StockAdjustment adjustment = StockAdjustment.builder()
                .product(product)
                .adjustmentType(request.getAdjustmentType())
                .quantity(request.getQuantity())
                .reason(request.getReason())
                .build();

        return toResponse(stockAdjustmentRepository.save(adjustment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockAdjustmentResponse> getAllAdjustments() {
        return stockAdjustmentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private StockAdjustmentResponse toResponse(StockAdjustment adjustment) {
        return StockAdjustmentResponse.builder()
                .id(adjustment.getId())
                .productId(adjustment.getProduct().getId())
                .productName(adjustment.getProduct().getName())
                .adjustmentType(adjustment.getAdjustmentType())
                .quantity(adjustment.getQuantity())
                .reason(adjustment.getReason())
                .createdAt(adjustment.getCreatedAt())
                .build();
    }
}
