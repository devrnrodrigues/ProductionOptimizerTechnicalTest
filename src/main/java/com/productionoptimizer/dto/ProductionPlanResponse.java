package com.productionoptimizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanResponse {
    private String productName;
    private int quantityToProduce;
    private double expectedProfit;
}