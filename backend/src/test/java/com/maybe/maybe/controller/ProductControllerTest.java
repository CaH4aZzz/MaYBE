package com.maybe.maybe.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybe.maybe.dto.ProductDTO;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getAllProductsTest() throws Exception {
        Product first = initProduct();

        Product second = new Product();
        second.setId(2L);
        second.setName("sandwich");
        second.setPrice(new BigDecimal(50));
        second.setComponentProducts(new HashSet<>());

        Product third = new Product();
        third.setId(3L);
        third.setName("soup");
        third.setPrice(new BigDecimal(77));
        third.setComponentProducts(new HashSet<>());

        List<Product> list = new ArrayList<>();
        list.add(first);
        list.add(second);
        list.add(third);

        Page<Product> pages = new PageImpl<>(list);
        given(productService.findAll(any(Pageable.class))).willReturn(pages);

        mvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(list.size())))
                .andExpect(jsonPath("$.content[0].id").value(pages.getContent().get(0).getId()))
                .andExpect(jsonPath("$.content[0].name").value(pages.getContent().get(0).getName()))
                .andExpect(jsonPath("$.content[0].price").value(first.getPrice()))
                .andExpect(jsonPath("$.content[0].componentProducts", hasSize(first.getComponentProducts().size())))
                .andExpect(jsonPath("$.content[1].id").value(second.getId()))
                .andExpect(jsonPath("$.content[1].name").value(second.getName()))
                .andExpect(jsonPath("$.content[1].price").value(second.getPrice()))
                .andExpect(jsonPath("$.content[1].componentProducts", hasSize(second.getComponentProducts().size())))
                .andExpect(jsonPath("$.content[2].id").value(third.getId()))
                .andExpect(jsonPath("$.content[2].name").value(third.getName()))
                .andExpect(jsonPath("$.content[2].price").value(third.getPrice()))
                .andExpect(jsonPath("$.content[2].componentProducts", hasSize(third.getComponentProducts().size())));


        verify(productService, times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void getProductByIdTest() throws Exception {
        Product product = initProduct();

        when(productService.findById(product.getId())).thenReturn(product);

        mvc.perform(get("/api/products/{productId}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.componentProducts", hasSize(product.getComponentProducts().size())));

        verify(productService, times(1)).findById(product.getId());
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void addProductTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        ProductDTO productDTO = initDTO();
        Product product = initProduct();
        when(productService.saveDTO(any(ProductDTO.class))).thenReturn(product);

        mvc.perform(post("/api/products")
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.componentProducts", hasSize(product.getComponentProducts().size())));
    }

    @Test
    public void updateProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = initProduct();
        ProductDTO productDTO = initDTO();

        given(productService.saveDTO(eq(product.getId()), any(ProductDTO.class))).willReturn(product);

        mvc.perform(put("/api/products/{productId}", product.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.componentProducts", hasSize(product.getComponentProducts().size())));
    }

    private Product initProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("salad");
        product.setPrice(new BigDecimal(100));
        return product;
    }

    private ProductDTO initDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("testName");
        productDTO.setPrice(new BigDecimal(777));
        return productDTO;
    }
}