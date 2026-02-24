package com.productionoptimizer.controller;

import com.productionoptimizer.model.ProductComposition;
import com.productionoptimizer.repository.ProductCompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product-compositions")
public class ProductCompositionController {

    @Autowired
    private ProductCompositionRepository repository;

    @GetMapping
    public List<ProductComposition> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ProductComposition create(@RequestBody ProductComposition pc) {
        return repository.save(pc);
    }
}