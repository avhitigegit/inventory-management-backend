package com.hardware.service.impl;

import com.hardware.dto.request.SaleItemRequest;
import com.hardware.dto.request.SaleRequest;
import com.hardware.dto.response.SaleItemResponse;
import com.hardware.dto.response.SaleResponse;
import com.hardware.entity.Customer;
import com.hardware.entity.Product;
import com.hardware.entity.Sale;
import com.hardware.entity.SaleItem;
import com.hardware.enums.PaymentType;
import com.hardware.exception.InsufficientStockException;
import com.hardware.exception.ResourceNotFoundException;
import com.hardware.repository.CustomerRepository;
import com.hardware.repository.ProductRepository;
import com.hardware.repository.SaleRepository;
import com.hardware.service.ProductService;
import com.hardware.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest request) {
        Customer customer = null;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", request.getCustomerId()));
        }

        List<SaleItem> saleItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal discount = request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO;

        for (SaleItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.getProductId()));

            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new InsufficientStockException(
                        product.getName(), itemRequest.getQuantity(), product.getQuantity());
            }

            BigDecimal itemTotal = product.getSellingPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            saleItems.add(SaleItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getSellingPrice())
                    .totalPrice(itemTotal)
                    .build());
        }

        Sale sale = Sale.builder()
                .customer(customer)
                .totalAmount(totalAmount)
                .discount(discount)
                .paidAmount(request.getPaidAmount())
                .paymentType(request.getPaymentType())
                .build();

        for (SaleItem item : saleItems) {
            item.setSale(sale);
            sale.getItems().add(item);
        }

        Sale savedSale = saleRepository.save(sale);

        for (SaleItemRequest itemRequest : request.getItems()) {
            productService.updateStock(itemRequest.getProductId(), -itemRequest.getQuantity());
        }

        if (customer != null && request.getPaymentType() == PaymentType.CREDIT) {
            BigDecimal outstanding = totalAmount.subtract(discount).subtract(request.getPaidAmount());
            if (outstanding.compareTo(BigDecimal.ZERO) > 0) {
                customer.setCreditBalance(customer.getCreditBalance().add(outstanding));
                customerRepository.save(customer);
            }
        }

        return toResponse(savedSale);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getSaleById(Long id) {
        return toResponse(saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getSalesByDateRange(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findByCreatedAtBetween(start, end).stream()
                .map(this::toResponse)
                .toList();
    }

    private SaleResponse toResponse(Sale sale) {
        List<SaleItemResponse> items = sale.getItems().stream()
                .map(item -> SaleItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        return SaleResponse.builder()
                .id(sale.getId())
                .customerId(sale.getCustomer() != null ? sale.getCustomer().getId() : null)
                .customerName(sale.getCustomer() != null ? sale.getCustomer().getName() : null)
                .items(items)
                .totalAmount(sale.getTotalAmount())
                .discount(sale.getDiscount())
                .paidAmount(sale.getPaidAmount())
                .paymentType(sale.getPaymentType())
                .createdAt(sale.getCreatedAt())
                .build();
    }
}
