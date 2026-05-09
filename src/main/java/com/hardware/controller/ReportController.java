package com.hardware.controller;

import com.hardware.dto.response.DashboardResponse;
import com.hardware.dto.response.LowStockResponse;
import com.hardware.dto.response.SaleResponse;
import com.hardware.dto.response.TopProductResponse;
import com.hardware.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(reportService.getDashboardSummary());
    }

    @GetMapping("/daily")
    public ResponseEntity<List<SaleResponse>> getDailyReport() {
        return ResponseEntity.ok(reportService.getDailySalesReport());
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<SaleResponse>> getMonthlyReport() {
        return ResponseEntity.ok(reportService.getMonthlySalesReport());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockResponse>> getLowStockReport() {
        return ResponseEntity.ok(reportService.getLowStockReport());
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductResponse>> getTopProducts() {
        return ResponseEntity.ok(reportService.getTopSellingProducts());
    }
}
