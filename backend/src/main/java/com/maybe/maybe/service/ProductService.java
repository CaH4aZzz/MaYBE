package com.maybe.maybe.service;

import com.maybe.maybe.dto.ProductDTO;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.ComponentRepository;
import com.maybe.maybe.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ComponentRepository componentRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    private Product convertDTOtoEntity(ProductDTO productDTO) {
        Long productId = null;
        return convertDTOtoEntity(productId, productDTO);
    }

    private Product convertDTOtoEntity(Long productId, ProductDTO productDTO) {
        Product product;
        if (productId != null) {
            product = findById(productId);
        } else {
            product = new Product();
            product.setComponentProducts(new HashSet<>());
        }
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product id=" + id));
    }

    public Product saveDTO(ProductDTO productDTO) {
        return productRepository.save(convertDTOtoEntity(productDTO));
    }

    public Product saveDTO(Long productId, ProductDTO productDTO) {
        return productRepository.save(convertDTOtoEntity(productId, productDTO));
    }
}
