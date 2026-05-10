package com.hardware.service;

import com.hardware.dto.request.CustomerPaymentRequest;
import com.hardware.dto.response.CustomerPaymentResponse;

import java.util.List;

public interface CustomerPaymentService {

    CustomerPaymentResponse settlePayment(CustomerPaymentRequest request);

    List<CustomerPaymentResponse> getPaymentsByCustomer(Long customerId);
}
