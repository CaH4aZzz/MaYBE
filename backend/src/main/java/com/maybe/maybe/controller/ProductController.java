package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.ProductDTO;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Statistic
    @GetMapping
    public Page<Product> getAllProducts(@PageableDefault Pageable pageable) {
        return productService.findAll(pageable);
    }

    @Statistic
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.saveDTO(productDTO), HttpStatus.CREATED);
    }

    @Statistic
    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @Statistic
    @PutMapping("/{productId}")
    public Product saveProduct(@PathVariable Long productId,
                                               @Valid @RequestBody ProductDTO productDTO) {
        return productService.saveDTO(productId, productDTO);
    }
}
