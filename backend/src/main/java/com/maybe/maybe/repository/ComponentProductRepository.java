package com.maybe.maybe.repository;

import com.maybe.maybe.entity.ComponentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponentProductRepository extends JpaRepository<ComponentProduct, Long> {

    public List<ComponentProduct> findAllByProductId(Long productId);
}