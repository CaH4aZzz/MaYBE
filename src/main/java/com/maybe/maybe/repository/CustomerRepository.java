package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Desk, Long> {
}