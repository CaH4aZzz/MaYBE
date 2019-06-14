package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, Long> {
}