package com.productionoptimizer.controller;

import com.productionoptimizer.dto.ProductionPlanResponse;
import com.productionoptimizer.service.ProductionOptimizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/production")
public class ProductionController {
    @Autowired
    private ProductionOptimizerService optimizerService;

    @GetMapping("/optimize")
    public List<ProductionPlanResponse> optimizeProduction() {
        return List.of(); // vazio por enquanto
    }
}