package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.ComponentProductDTO;
import com.maybe.maybe.entity.ComponentProduct;
import com.maybe.maybe.service.ComponentProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ComponentProductController {

    private ComponentProductService componentProductService;

    public ComponentProductController(ComponentProductService componentProductService) {
        this.componentProductService = componentProductService;
    }

    @Statistic
    @GetMapping("/{productId}/components")
    public List<ComponentProduct> getAllComponentProducts(@PathVariable Long productId) {
        return componentProductService.findAllByProductId(productId);
    }

    @Statistic
    @PostMapping("/{productId}/components")
    public ResponseEntity<ComponentProduct> addComponentProduct(@PathVariable Long productId,
                                                                @Valid @RequestBody ComponentProductDTO componentProductDTO) {
        return new ResponseEntity<>(componentProductService.saveDTO(productId, componentProductDTO),
                HttpStatus.CREATED);
    }

    @Statistic
    @GetMapping("/components/{componentProductId}")
    public ComponentProduct getComponentProduct(@PathVariable Long componentProductId) {
        return componentProductService.findById(componentProductId);
    }

    @Statistic
    @PutMapping("/components/{componentProductId}")
    public ComponentProduct saveComponentProduct(@PathVariable Long componentProductId,
                                                 @Valid @RequestBody ComponentProductDTO componentProductDTO) {
        return componentProductService.updateDTO(componentProductId, componentProductDTO);
    }

}
