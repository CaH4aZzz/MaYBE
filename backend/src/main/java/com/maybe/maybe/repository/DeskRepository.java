package com.maybe.maybe.repository;

import com.maybe.maybe.entity.Desk;

import com.maybe.maybe.entity.enums.DeskState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    Desk findDeskById(Long id);

    Desk findDeskByName(String name);

    List<Desk> findAllByDeskState(DeskState state);

}

