package com.productionoptimizer.service;

import com.productionoptimizer.exception.ResourceNotFoundException;
import com.productionoptimizer.model.Product;
import com.productionoptimizer.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product product) {

        Product existing = getById(id);

        existing.setName(product.getName());
        existing.setSalePrice(product.getSalePrice());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}