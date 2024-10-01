package com.example.paymentsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartSummaryDto {
    private int totalPrice;
    private int totalSavings;
}
