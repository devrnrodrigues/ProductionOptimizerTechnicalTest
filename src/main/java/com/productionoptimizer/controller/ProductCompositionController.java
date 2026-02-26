package com.productionoptimizer.controller;

import com.productionoptimizer.model.ProductComposition;
import com.productionoptimizer.service.ProductCompositionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-compositions")
public class ProductCompositionController {

    private final ProductCompositionService service;

    public ProductCompositionController(ProductCompositionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductComposition> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductComposition getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ProductComposition create(@Valid @RequestBody ProductComposition pc) {
        return service.save(pc);
    }

    @PutMapping("/{id}")
    public ProductComposition update(@PathVariable Long id,
                                     @Valid @RequestBody ProductComposition pc) {
        return service.update(id, pc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}