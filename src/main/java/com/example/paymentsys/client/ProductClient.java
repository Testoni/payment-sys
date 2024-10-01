package com.example.paymentsys.client;

import com.example.paymentsys.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "productClient", url = "http://localhost:8081")
public interface ProductClient {

    @GetMapping("/products")
    List<ProductDto> getAllProducts();

    @GetMapping("/products/{productId}")
    ProductDto getProductById(@PathVariable("productId") String productId);

}
