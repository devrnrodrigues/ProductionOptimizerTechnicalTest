package com.productionoptimizer.controller;

import com.productionoptimizer.dto.ProductionPlanResponse;
import com.productionoptimizer.service.ProductionOptimizerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private final ProductionOptimizerService optimizerService;

    public ProductionController(
            ProductionOptimizerService optimizerService) {
        this.optimizerService = optimizerService;
    }

    @GetMapping("/optimize")
    public List<ProductionPlanResponse> optimizeProduction() {
        return optimizerService.optimizeProduction();
    }
}