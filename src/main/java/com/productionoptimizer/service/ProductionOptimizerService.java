package com.productionoptimizer.service;

import com.productionoptimizer.dto.ProductionPlanResponse;
import com.productionoptimizer.model.Product;
import com.productionoptimizer.model.ProductComposition;
import com.productionoptimizer.model.RawMaterial;
import com.productionoptimizer.repository.ProductCompositionRepository;
import com.productionoptimizer.repository.ProductRepository;
import com.productionoptimizer.repository.RawMaterialRepository;
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

        // Current stock map
        Map<Long, BigDecimal> stockMap = materials.stream()
                .collect(Collectors.toMap(
                        RawMaterial::getId,
                        RawMaterial::getStockQuantity
                ));

        // Calculate profit and efficiency
        List<ProductProfit> sortedProducts = products.stream()
                .map(product -> {
                    BigDecimal profit = calculateProfit(product);

                    if (profit.compareTo(BigDecimal.ZERO) <= 0) {
                        return null;
                    }

                    BigDecimal efficiency = calculateEfficiency(product, profit);

                    return new ProductProfit(product, profit, efficiency);
                })
                .filter(Objects::nonNull)
                .sorted((a, b) -> b.efficiency.compareTo(a.efficiency))
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

    private BigDecimal calculateEfficiency(Product product,
                                           BigDecimal profit) {

        List<ProductComposition> compositions =
                compositionRepository.findByProductId(product.getId());

        BigDecimal totalMaterialUsed = BigDecimal.ZERO;

        for (ProductComposition comp : compositions) {
            totalMaterialUsed = totalMaterialUsed
                    .add(comp.getQuantityRequired());
        }

        if (totalMaterialUsed.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return profit.divide(totalMaterialUsed, 4, RoundingMode.HALF_UP);
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
        BigDecimal efficiency;

        public ProductProfit(Product product,
                             BigDecimal profit,
                             BigDecimal efficiency) {
            this.product = product;
            this.profit = profit;
            this.efficiency = efficiency;
        }
    }
}