package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentProductDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.ComponentProduct;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.ComponentProductRepository;
import com.maybe.maybe.repository.ComponentRepository;
import com.maybe.maybe.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ComponentProductService {
    private ComponentProductRepository componentProductRepository;
    private ProductRepository productRepository;
    private ComponentRepository componentRepository;

    public ComponentProductService(ComponentProductRepository componentProductRepository,
                                   ProductRepository productRepository, ComponentRepository componentRepository) {
        this.componentProductRepository = componentProductRepository;
        this.productRepository = productRepository;
        this.componentRepository = componentRepository;
    }

    public List<ComponentProduct> findAllByProductId(Long productId) {
        return componentProductRepository.findAllByProductId(productId);
    }

    public ComponentProduct findById(Long componentProductId) {
        return componentProductRepository.findById(componentProductId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find component product id=" + componentProductId));
    }

    public ComponentProduct saveDTO(Long productId, ComponentProductDTO componentProductDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find product id=" + productId));
        ComponentProduct componentProductEntity = new ComponentProduct();
        componentProductEntity.setProduct(product);
        ComponentProduct componentProduct = convertDTOtoEntity(componentProductDTO,
                componentProductEntity);
        return componentProductRepository.save(componentProduct);
    }

    public ComponentProduct updateDTO(Long componentProductId, ComponentProductDTO componentProductDTO) {
        ComponentProduct componentProduct = findById(componentProductId);
        ComponentProduct componentProductEntity = convertDTOtoEntity(componentProductDTO, componentProduct);
        return componentProductRepository.save(componentProductEntity);
    }

    private ComponentProduct convertDTOtoEntity(ComponentProductDTO componentProductDTO,
                                                ComponentProduct componentProduct) {
        Long componentId = componentProductDTO.getComponentId();
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find component id=" + componentId));
        componentProduct.setComponent(component);
        componentProduct.setQuantity(componentProductDTO.getQuantity());
        return componentProduct;
    }

}
