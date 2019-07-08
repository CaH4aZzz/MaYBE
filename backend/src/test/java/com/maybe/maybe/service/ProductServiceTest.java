package com.maybe.maybe.service;

import com.maybe.maybe.dto.ProductDTO;
import com.maybe.maybe.entity.Product;
import com.maybe.maybe.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product expected;
    private ProductDTO productDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);

        expected = new Product();
        expected.setId(17L);
        expected.setName("product1");
        expected.setPrice(new BigDecimal(20.20));
    }

    @Test
    public void findAll() {
        Product expectedTwo = new Product();
        expectedTwo.setId(18L);
        expectedTwo.setName("product2");
        expectedTwo.setPrice(new BigDecimal(10));

        Product expectedThree = new Product();
        expectedThree.setId(18L);
        expectedThree.setName("product3");
        expectedThree.setPrice(new BigDecimal(666.34));

        List<Product> expectedList = new ArrayList<>();
        expectedList.add(expected);
        expectedList.add(expectedTwo);
        expectedList.add(expectedThree);

        Page<Product> expectedProducts = new PageImpl<>(expectedList);
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(pageable)).thenReturn(expectedProducts);

        Page<Product> actualProducts = productService.findAll(pageable);

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void findByIdTest() {
        Long id = 17L;
        when(productRepository.findById(id)).thenReturn(Optional.of(expected));
        Product actual = productService.findById(id);

        assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdWhenResultNullTest() {
        Long id = 17L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        productService.findById(id);
    }

    @Test
    public void saveDTOTest() {
        productDTO = new ProductDTO();
        productDTO.setName("product1");
        productDTO.setPrice(new BigDecimal(20.20));

        when(productRepository.save(any(Product.class))).thenReturn(expected);
        Product actual = productService.saveDTO(productDTO);

        assertEquals(expected, actual);
    }

    @Test
    public void saveDTOWhenIdGivenTest(){
        productDTO = new ProductDTO();
        productDTO.setName("expected");
        productDTO.setPrice(new BigDecimal(100));

        Product expected = new Product();
        Long id = 10001L;
        expected.setId(id);
        expected.setName(productDTO.getName());
        expected.setPrice(productDTO.getPrice());

        when(productRepository.findById(id)).thenReturn(Optional.of(expected));
        when(productRepository.save(any(Product.class))).thenReturn(expected);

        Product actual = productService.saveDTO(id, productDTO);
        assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void saveDTOWhenIdGivenWithExceptionTest() {
        productDTO = new ProductDTO();
        productDTO.setName("product1");
        productDTO.setPrice(new BigDecimal(20.20));

        Product actual = productService.saveDTO(1L, productDTO);
    }
}