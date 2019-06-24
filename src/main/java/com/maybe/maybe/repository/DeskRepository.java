package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk, Long> {

    Desk findDeskById(Long id);
}