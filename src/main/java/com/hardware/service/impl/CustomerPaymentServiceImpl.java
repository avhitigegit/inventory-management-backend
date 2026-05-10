package com.hardware.service.impl;

import com.hardware.dto.request.CustomerPaymentRequest;
import com.hardware.dto.response.CustomerPaymentResponse;
import com.hardware.entity.Customer;
import com.hardware.entity.CustomerPayment;
import com.hardware.exception.ResourceNotFoundException;
import com.hardware.repository.CustomerPaymentRepository;
import com.hardware.repository.CustomerRepository;
import com.hardware.service.CustomerPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

    private final CustomerPaymentRepository customerPaymentRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerPaymentResponse settlePayment(CustomerPaymentRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", request.getCustomerId()));

        if (customer.getCreditBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    customer.getName() + " has no outstanding balance to settle");
        }

        if (request.getAmount().compareTo(customer.getCreditBalance()) > 0) {
            throw new IllegalArgumentException(
                    "Payment amount exceeds outstanding balance of Rs. " + customer.getCreditBalance());
        }

        customer.setCreditBalance(customer.getCreditBalance().subtract(request.getAmount()));
        customerRepository.save(customer);

        CustomerPayment payment = CustomerPayment.builder()
                .customer(customer)
                .amount(request.getAmount())
                .note(request.getNote())
                .build();
        CustomerPayment saved = customerPaymentRepository.save(payment);

        return toResponse(saved, customer.getCreditBalance());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentResponse> getPaymentsByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", customerId);
        }
        return customerPaymentRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(p -> toResponse(p, p.getCustomer().getCreditBalance()))
                .toList();
    }

    private CustomerPaymentResponse toResponse(CustomerPayment payment, BigDecimal remainingBalance) {
        return CustomerPaymentResponse.builder()
                .id(payment.getId())
                .customerId(payment.getCustomer().getId())
                .customerName(payment.getCustomer().getName())
                .amount(payment.getAmount())
                .note(payment.getNote())
                .remainingBalance(remainingBalance)
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
