package com.hardware.service;

import com.hardware.dto.request.ProductRequest;
import com.hardware.dto.response.LowStockResponse;
import com.hardware.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse getProductByBarcode(String barcode);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<LowStockResponse> getLowStockProducts();

    void updateStock(Long productId, int quantityChange);
}
