package com.productionoptimizer.service;

import com.productionoptimizer.dto.ProductionPlanResponse;
import com.productionoptimizer.model.*;
import com.productionoptimizer.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductionOptimizerService {

    private final ProductRepository productRepository;
    private final ProductCompositionRepository compositionRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductionOptimizerService(ProductRepository productRepository,
                                      ProductCompositionRepository compositionRepository,
                                      RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.compositionRepository = compositionRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductionPlanResponse> optimizeProduction() {

        List<Product> products = productRepository.findAll();
        List<RawMaterial> materials = rawMaterialRepository.findAll();

        Map<Long, BigDecimal> stockMap = materials.stream()
                .collect(Collectors.toMap(
                        RawMaterial::getId,
                        RawMaterial::getStockQuantity
                ));

        List<ProductProfit> sortedProducts = products.stream()
                .map(p -> new ProductProfit(p, calculateProfit(p)))
                .filter(pp -> pp.profit.compareTo(BigDecimal.ZERO) > 0)
                .sorted((a, b) -> b.profit.compareTo(a.profit))
                .toList();

        List<ProductionPlanResponse> response = new ArrayList<>();

        for (ProductProfit pp : sortedProducts) {

            int quantity = calculateMaxProduction(pp.product, stockMap);

            if (quantity > 0) {

                updateStock(pp.product, quantity, stockMap);

                response.add(new ProductionPlanResponse(
                        pp.product.getName(),
                        quantity,
                        pp.profit.multiply(BigDecimal.valueOf(quantity))
                ));
            }
        }

        return response;
    }

    private BigDecimal calculateProfit(Product product) {

        List<ProductComposition> compositions =
                compositionRepository.findByProductId(product.getId());

        BigDecimal totalCost = BigDecimal.ZERO;

        for (ProductComposition comp : compositions) {
            totalCost = totalCost.add(
                    comp.getQuantityRequired()
                            .multiply(comp.getRawMaterial().getUnitCost())
            );
        }

        return product.getSalePrice().subtract(totalCost);
    }

    private int calculateMaxProduction(Product product,
                                       Map<Long, BigDecimal> stockMap) {

        List<ProductComposition> compositions =
                compositionRepository.findByProductId(product.getId());

        int maxUnits = Integer.MAX_VALUE;

        for (ProductComposition comp : compositions) {

            BigDecimal available =
                    stockMap.getOrDefault(
                            comp.getRawMaterial().getId(),
                            BigDecimal.ZERO);

            BigDecimal required = comp.getQuantityRequired();

            int possible = available
                    .divide(required, 0, RoundingMode.DOWN)
                    .intValue();

            maxUnits = Math.min(maxUnits, possible);
        }

        return maxUnits == Integer.MAX_VALUE ? 0 : maxUnits;
    }

    private void updateStock(Product product,
                             int quantity,
                             Map<Long, BigDecimal> stockMap) {

        List<ProductComposition> compositions =
                compositionRepository.findByProductId(product.getId());

        for (ProductComposition comp : compositions) {

            Long materialId =
                    comp.getRawMaterial().getId();

            BigDecimal used =
                    comp.getQuantityRequired()
                            .multiply(BigDecimal.valueOf(quantity));

            stockMap.put(materialId,
                    stockMap.get(materialId).subtract(used));
        }
    }

    private static class ProductProfit {
        Product product;
        BigDecimal profit;

        public ProductProfit(Product product, BigDecimal profit) {
            this.product = product;
            this.profit = profit;
        }
    }
}