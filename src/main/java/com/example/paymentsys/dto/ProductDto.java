package com.example.paymentsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private int price;
    private List<PromotionDto> promotions;
}
