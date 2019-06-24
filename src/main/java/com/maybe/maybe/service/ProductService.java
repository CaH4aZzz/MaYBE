package com.maybe.maybe.service;

import com.maybe.maybe.dto.ComponentProductDTO;
import com.maybe.maybe.dto.ProductDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.ComponentProduct;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.ComponentRepository;
import com.maybe.maybe.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ComponentRepository componentRepository;
    private ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        Type listType = new TypeToken<List<ProductDTO>>() {
        }.getType();
        return modelMapper.map(products, listType);
    }

    private Product convertDTOtoEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        List<ComponentProduct> componentProducts = productDTO.getComponentProductDTOs().stream()
                .map(c -> convertComponentProductDTOtoEntity(c, product)).collect(Collectors.toList());
        product.setComponentProducts(componentProducts);
        return product;
    }

    private ComponentProduct convertComponentProductDTOtoEntity(ComponentProductDTO componentProductDTO,
                                                                Product product) {
        ComponentProduct componentProduct = new ComponentProduct();
        componentProduct.setId(componentProductDTO.getId());
        Optional<Component> componentOptional = componentRepository.findById(componentProductDTO.getComponentId());
        componentOptional.ifPresent(componentProduct::setComponent);
        componentProduct.setProduct(product);
        componentProduct.setQuantity(componentProductDTO.getQuantity());
        return componentProduct;
    }

    private ProductDTO convertEntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        List<ComponentProductDTO> componentProducts = product.getComponentProducts().stream()
                .map(this::convertComponentProductEntityToDTO).collect(Collectors.toList());
        productDTO.setComponentProductDTOs(componentProducts);
        return productDTO;
    }

    private ComponentProductDTO convertComponentProductEntityToDTO(ComponentProduct componentProduct) {
        ComponentProductDTO componentProductDTO = new ComponentProductDTO();
        componentProductDTO.setId(componentProduct.getId());
        componentProductDTO.setComponentId(componentProduct.getProduct().getId());
        componentProductDTO.setComponentName(componentProduct.getProduct().getName());
        componentProductDTO.setQuantity(componentProduct.getQuantity());
        return componentProductDTO;
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Type listType = new TypeToken<List<ComponentProduct>>() {
        }.getType();
        product.setComponentProducts(modelMapper.map(productDTO.getComponentProductDTOs(), listType));
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
