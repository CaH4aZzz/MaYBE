package com.maybe.maybe.repository;

import com.maybe.maybe.entity.ComponentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentProductRepository extends JpaRepository<ComponentProduct, Long> {
}