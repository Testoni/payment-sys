package com.example.paymentsys.service;

import com.example.paymentsys.client.ProductClient;
import com.example.paymentsys.dto.ProductDto;
import com.example.paymentsys.exception.NotFoundException;
import com.example.paymentsys.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductClient productClient;

    public List<ProductDto> fetchAllProducts() {
        try {
            List<ProductDto> products = productClient.getAllProducts();
            if (products == null || products.isEmpty()) {
                throw new NotFoundException("No products found.");
            }
            return products;
        } catch (Exception e) {
            throw new ServiceException("Error fetching products: " + e.getMessage());
        }
    }

    public ProductDto fetchProductById(String productId) {
        try {
            ProductDto product = productClient.getProductById(productId);
            return Optional.ofNullable(product).orElseThrow(() -> new NotFoundException("Product with ID " + productId + " not found."));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error fetching product by ID: " + e.getMessage());
        }
    }
}
