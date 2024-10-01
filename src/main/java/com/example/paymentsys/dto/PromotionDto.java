package com.example.paymentsys.dto;

import com.example.paymentsys.enums.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromotionDto {
    private String id;
    private PromotionType type;
    private int requiredQty;
    private int freeQty;
}
