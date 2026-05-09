package com.hardware.repository;

import com.hardware.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    Optional<Product> findByBarcode(String barcode);

    boolean existsBySku(String sku);

    boolean existsByBarcode(String barcode);

    List<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.quantity <= p.reorderLevel AND p.active = true")
    List<Product> findLowStockProducts();
}
