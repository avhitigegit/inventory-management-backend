package com.hardware.repository;

import com.hardware.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    @Query("""
            SELECT si.product.id, si.product.name, SUM(si.quantity) AS totalSold
            FROM SaleItem si
            WHERE si.sale.createdAt BETWEEN :start AND :end
            GROUP BY si.product.id, si.product.name
            ORDER BY totalSold DESC
            """)
    List<Object[]> findTopSellingProducts(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);
}
