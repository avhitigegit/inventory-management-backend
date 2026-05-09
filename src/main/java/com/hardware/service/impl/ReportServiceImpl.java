package com.hardware.service.impl;

import com.hardware.dto.response.DashboardResponse;
import com.hardware.dto.response.LowStockResponse;
import com.hardware.dto.response.SaleResponse;
import com.hardware.dto.response.TopProductResponse;
import com.hardware.repository.CustomerRepository;
import com.hardware.repository.ProductRepository;
import com.hardware.repository.SaleItemRepository;
import com.hardware.repository.SaleRepository;
import com.hardware.service.ProductService;
import com.hardware.service.ReportService;
import com.hardware.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SaleService saleService;
    private final ProductService productService;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboardSummary() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        BigDecimal todaySales = saleRepository.sumTotalAmountBetween(startOfDay, endOfDay);
        long totalProducts = productRepository.findByActiveTrue().size();
        long lowStockCount = productRepository.findLowStockProducts().size();
        long totalCustomers = customerRepository.count();

        return DashboardResponse.builder()
                .todaySales(todaySales)
                .totalProducts(totalProducts)
                .lowStockCount(lowStockCount)
                .totalCustomers(totalCustomers)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getDailySalesReport() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1).minusNanos(1);
        return saleService.getSalesByDateRange(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> getMonthlySalesReport() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusNanos(1);
        return saleService.getSalesByDateRange(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LowStockResponse> getLowStockReport() {
        return productService.getLowStockProducts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopProductResponse> getTopSellingProducts() {
        LocalDateTime start = LocalDate.now().minusDays(30).atStartOfDay();
        LocalDateTime end = LocalDateTime.now();

        return saleItemRepository.findTopSellingProducts(start, end).stream()
                .map(row -> TopProductResponse.builder()
                        .productId(((Number) row[0]).longValue())
                        .productName((String) row[1])
                        .totalSold(((Number) row[2]).longValue())
                        .build())
                .toList();
    }
}
