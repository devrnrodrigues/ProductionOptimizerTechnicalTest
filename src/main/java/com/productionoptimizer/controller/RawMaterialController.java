package com.productionoptimizer.controller;

import com.productionoptimizer.model.RawMaterial;
import com.productionoptimizer.service.RawMaterialService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public List<RawMaterial> getAll() {
        return rawMaterialService.getAll();
    }

    @GetMapping("/{id}")
    public RawMaterial getById(@PathVariable Long id) {
        return rawMaterialService.getById(id);
    }

    @PostMapping
    public RawMaterial create(@Valid @RequestBody RawMaterial rawMaterial) {
        return rawMaterialService.save(rawMaterial);
    }

    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id,
                              @Valid @RequestBody RawMaterial rawMaterial) {
        return rawMaterialService.update(id, rawMaterial);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
    }
}