package com.maybe.maybe.service;

import com.maybe.maybe.repository.ComponentRepository;
import org.springframework.stereotype.Service;

@Service
public class ComponentService {
    private ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }
}
