package com.maybe.maybe.service;

import com.maybe.maybe.repository.ComponentProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ComponentProductService {
    private ComponentProductRepository componentProductRepository;

    public ComponentProductService(ComponentProductRepository componentProductRepository) {
        this.componentProductRepository = componentProductRepository;
    }
}
