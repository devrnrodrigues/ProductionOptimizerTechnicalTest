package com.productionoptimizer.service;

import com.productionoptimizer.dto.ProductionPlanResponse;
import com.productionoptimizer.model.Product;
import com.productionoptimizer.model.ProductComposition;
import com.productionoptimizer.model.RawMaterial;
import com.productionoptimizer.repository.ProductCompositionRepository;
import com.productionoptimizer.repository.ProductRepository;
import com.productionoptimizer.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductionOptimizerServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCompositionRepository compositionRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductionOptimizerService service;

    private Product productA;
    private RawMaterial material;

    @BeforeEach
    void setup() {

        material = new RawMaterial(
                1L,
                "Steel",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(10)
        );

        productA = new Product(
                1L,
                "Product A",
                BigDecimal.valueOf(100)
        );

        ProductComposition composition =
                new ProductComposition(
                        1L,
                        productA,
                        material,
                        BigDecimal.valueOf(2)
                );

        when(productRepository.findAll())
                .thenReturn(List.of(productA));

        when(rawMaterialRepository.findAll())
                .thenReturn(List.of(material));

        when(compositionRepository.findByProductId(1L))
                .thenReturn(List.of(composition));
    }

    @Test
    void shouldProduceProductWithPositiveProfit() {

        List<ProductionPlanResponse> result =
                service.optimizeProduction();

        assertEquals(1, result.size());
        assertEquals("Product A",
                result.get(0).getProductName());
    }
}