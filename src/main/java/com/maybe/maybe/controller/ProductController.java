package com.maybe.maybe.controller;

import com.maybe.maybe.dto.ProductDTO;
import com.maybe.maybe.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestParam(name = "state", defaultValue = "all") String state) {
        return productService.findAllProducts();
    }

    @PostMapping
    public ProductDTO addProduct(ProductDTO productDTO) {
        productDTO.setId(null);
        productDTO.getComponentProductDTOs().stream().forEach(c -> c.setId(null));
        return productService.save(productDTO);
    }

}
