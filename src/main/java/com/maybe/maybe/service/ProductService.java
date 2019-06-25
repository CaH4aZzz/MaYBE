package com.maybe.maybe.service;

import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id){
       return productRepository.getProductById(id);
    }
}
