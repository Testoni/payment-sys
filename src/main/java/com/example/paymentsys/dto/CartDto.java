package com.example.paymentsys.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private List<ProductDto> items;
    private int totalSavings;
}
