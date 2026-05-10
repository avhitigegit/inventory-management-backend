package com.hardware.controller;

import com.hardware.dto.request.CustomerPaymentRequest;
import com.hardware.dto.response.CustomerPaymentResponse;
import com.hardware.service.CustomerPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-payments")
@RequiredArgsConstructor
public class CustomerPaymentController {

    private final CustomerPaymentService customerPaymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'CASHIER')")
    public ResponseEntity<CustomerPaymentResponse> settlePayment(
            @Valid @RequestBody CustomerPaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerPaymentService.settlePayment(request));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'CASHIER')")
    public ResponseEntity<List<CustomerPaymentResponse>> getPaymentsByCustomer(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(customerPaymentService.getPaymentsByCustomer(customerId));
    }
}
