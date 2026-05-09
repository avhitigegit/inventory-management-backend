package com.hardware.repository;

import com.hardware.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByCreditBalanceGreaterThan(BigDecimal amount);
}
