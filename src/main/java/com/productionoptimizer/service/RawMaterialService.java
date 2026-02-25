package com.productionoptimizer.service;

import com.productionoptimizer.exception.ResourceNotFoundException;
import com.productionoptimizer.model.RawMaterial;
import com.productionoptimizer.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterialService(RawMaterialRepository repository) {
        this.repository = repository;
    }

    public List<RawMaterial> getAll() {
        return repository.findAll();
    }

    public RawMaterial getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Raw material not found"));
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