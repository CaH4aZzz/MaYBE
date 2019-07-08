package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}