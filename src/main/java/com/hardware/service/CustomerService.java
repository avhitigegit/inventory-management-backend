package com.hardware.service;

import com.hardware.dto.request.CustomerRequest;
import com.hardware.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    List<CustomerResponse> getCustomersWithCredit();
}
