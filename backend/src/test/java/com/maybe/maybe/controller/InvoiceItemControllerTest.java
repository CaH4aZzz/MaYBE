package com.maybe.maybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybe.maybe.dto.InvoiceItemDTO;
import com.maybe.maybe.entity.Component;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.InvoiceItem;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.entity.enums.Measure;
import com.maybe.maybe.service.InvoiceItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InvoiceItemController.class, secure = false)
public class InvoiceItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InvoiceItemService invoiceItemService;

    @Autowired
    ObjectMapper objectMapper;

    private InvoiceItemDTO invoiceItemDTO;
    private Invoice invoice;
    private InvoiceItem invoiceItem1;
    private InvoiceItem invoiceItem2;

    @Before
    public void setUp() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Ivanov");

        Component component1 = new Component();
        component1.setId(1L);
        component1.setName("Component1");
        component1.setTotal(BigDecimal.valueOf(3));
        component1.setQuantity(BigDecimal.valueOf(15));
        component1.setMeasure(Measure.PIESES);

        Component component2 = new Component();
        component2.setId(2L);
        component2.setName("Component1");
        component2.setTotal(BigDecimal.valueOf(3));
        component2.setQuantity(BigDecimal.valueOf(15));
        component2.setMeasure(Measure.PIESES);

        invoiceItemDTO = new InvoiceItemDTO();
        invoiceItemDTO.setComponentId(component1.getId());
        invoiceItemDTO.setPrice(BigDecimal.valueOf(5));
        invoiceItemDTO.setQuantity(BigDecimal.valueOf(10));

        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setName("001");
        invoice.setEmployee(employee);
        invoice.setInvoiceType(InvoiceType.INCOME);
        invoice.setDateCreated(LocalDateTime.now());

        invoiceItem1 = new InvoiceItem();
        invoiceItem1.setId(1L);
        invoiceItem1.setInvoice(invoice);
        invoiceItem1.setComponent(component1);
        invoiceItem1.setPrice(BigDecimal.valueOf(4));
        invoiceItem1.setQuantity(BigDecimal.valueOf(16));

        invoiceItem2 = new InvoiceItem();
        invoiceItem2.setId(2L);
        invoiceItem2.setInvoice(invoice);
        invoiceItem2.setComponent(component2);
        invoiceItem2.setPrice(BigDecimal.valueOf(7));
        invoiceItem2.setQuantity(BigDecimal.valueOf(12));
    }

    @Test
    public void getInvoiceItems() throws Exception {
        Long invoiceId = invoice.getId();
        Page<InvoiceItem> pages = new PageImpl<>(Arrays.asList(invoiceItem1, invoiceItem2));

        given(invoiceItemService.findAllByInvoice_Id(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(Pageable.class))).willReturn(pages);

        mvc.perform(get("/api/invoices/{invoiceId}/invoiceItems", invoiceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].component.name",
                        is(pages.getContent().get(0).getComponent().getName())));
    }

    @Test
    public void getInvoiceItem() throws Exception {
        Long invoiceItemId = invoiceItem1.getId();
        given(invoiceItemService.findById(invoiceItemId)).willReturn(invoiceItem1);

        mvc.perform(get("/api/invoiceItems/{invoiceItemId}", invoiceItemId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(invoiceItem1.getId().intValue())))
                .andExpect(jsonPath("$.component.name",
                        is(invoiceItem1.getComponent().getName())));
    }

    @Test
    public void createInvoiceItem() throws Exception {
        Long invoiceId = invoice.getId();
        given(invoiceItemService.createFromDTO(
                ArgumentMatchers.any(InvoiceItemDTO.class),
                ArgumentMatchers.anyLong()))
                .willReturn(invoiceItem1);

        mvc.perform(post("/api/invoices/{invoiceId}/invoiceItems", invoiceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceItemDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(invoiceItem1.getId().intValue())))
                .andExpect(jsonPath("$.component.name",
                        is(invoiceItem1.getComponent().getName())));
    }

    @Test
    public void updateInvoiceItem() throws Exception {
        Long invoiceItemId = invoiceItem1.getId();
        given(invoiceItemService.findById(invoiceItemId)).willReturn(invoiceItem1);
        given(invoiceItemService.updateFromDTO(invoiceItem1, invoiceItemDTO)).willReturn(invoiceItem1);

        mvc.perform(put("/api/invoiceItems/{invoiceItemId}", invoiceItemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(invoiceItem1.getId().intValue())))
                .andExpect(jsonPath("$.component.name",
                        is(invoiceItem1.getComponent().getName())));
    }

    @Test
    public void deleteInvoiceItem() throws Exception {
        Long invoiceItemId = invoiceItem1.getId();
        given(invoiceItemService.findById(invoiceItemId)).willReturn(invoiceItem1);

        mvc.perform(delete("/api/invoiceItems/{invoiceItemId}", invoiceItemId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(invoiceItem1.getId().intValue())))
                .andExpect(jsonPath("$.component.name",
                        is(invoiceItem1.getComponent().getName())));
    }
}