package com.hardware.service;

import com.hardware.dto.response.DashboardResponse;
import com.hardware.dto.response.LowStockResponse;
import com.hardware.dto.response.SaleResponse;
import com.hardware.dto.response.TopProductResponse;

import java.util.List;

public interface ReportService {

    DashboardResponse getDashboardSummary();

    List<SaleResponse> getDailySalesReport();

    List<SaleResponse> getMonthlySalesReport();

    List<LowStockResponse> getLowStockReport();

    List<TopProductResponse> getTopSellingProducts();
}
