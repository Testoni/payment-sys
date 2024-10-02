package com.example.paymentsys.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private BigDecimal totalPrice;
    private BigDecimal totalSavings;
}
