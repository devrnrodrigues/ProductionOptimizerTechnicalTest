package com.productionoptimizer.service;

import com.productionoptimizer.model.RawMaterial;
import com.productionoptimizer.repository.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RawMaterialService {
    @Autowired
    private RawMaterialRepository repository;

    public List<RawMaterial> getAll() {
        return repository.findAll();
    }

    public RawMaterial getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found"));
    }

    public RawMaterial save(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    public RawMaterial update(Long id, RawMaterial rawMaterial) {
        RawMaterial existing = getById(id);
        existing.setName(rawMaterial.getName());
        existing.setStockQuantity(rawMaterial.getStockQuantity());
        existing.setUnitCost(rawMaterial.getUnitCost());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}