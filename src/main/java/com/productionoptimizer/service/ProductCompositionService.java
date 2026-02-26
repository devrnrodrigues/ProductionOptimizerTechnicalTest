package com.productionoptimizer.service;

import com.productionoptimizer.exception.ResourceNotFoundException;
import com.productionoptimizer.model.ProductComposition;
import com.productionoptimizer.repository.ProductCompositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCompositionService {

    private final ProductCompositionRepository repository;

    public ProductCompositionService(ProductCompositionRepository repository) {
        this.repository = repository;
    }

    public List<ProductComposition> getAll() {
        return repository.findAll();
    }

    public ProductComposition getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Composition not found"));
    }

    public ProductComposition save(ProductComposition pc) {
        return repository.save(pc);
    }

    public ProductComposition update(Long id, ProductComposition pc) {

        ProductComposition existing = getById(id);

        existing.setProduct(pc.getProduct());
        existing.setRawMaterial(pc.getRawMaterial());
        existing.setQuantityRequired(pc.getQuantityRequired());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}