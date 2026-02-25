package com.productionoptimizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanResponse {
    private String productName;
    private int quantityToProduce;
    private BigDecimal expectedProfit;
}