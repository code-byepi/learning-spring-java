package com.app.springbootcrud.services;

import com.app.springbootcrud.entities.Product;
import com.app.springbootcrud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> delete(Product product) {
        Optional<Product> productOptional = productRepository.findById(product.getId());
        productOptional.ifPresent(productDb -> {
            productRepository.delete(productDb);
        });
        return productOptional;

    }

    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
          Product productDb = productOptional.orElseThrow();

          productDb.setName(product.getName());
          productDb.setDescription(product.getDescription());
          productDb.setPrice(product.getPrice());
          return Optional.of(productRepository.save(productDb));
        };
        return productOptional;
    }
}
