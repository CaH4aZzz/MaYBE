package com.maybe.maybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybe.maybe.dto.InvoiceDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.Invoice;
import com.maybe.maybe.entity.enums.InvoiceType;
import com.maybe.maybe.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InvoiceController.class, secure = false)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    ObjectMapper objectMapper;

    private InvoiceDTO invoiceDTO;
    private Invoice invoice;

    @Before
    public void setUp() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Ivanov");

        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEmployeeId(employee.getId());
        invoiceDTO.setInvoiceTypeId(InvoiceType.INCOME.getId());
        invoiceDTO.setName("007");

        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setName(invoiceDTO.getName());
        invoice.setEmployee(employee);
        invoice.setInvoiceType(InvoiceType.INCOME);
        invoice.setDateCreated(LocalDateTime.now());
    }

    @Test
    public void getInvoices() throws Exception {
        Page<Invoice> pages = new PageImpl<>(Collections.singletonList(invoice));

        given(invoiceService.findAllByPeriod(
                ArgumentMatchers.any(LocalDateTime.class),
                ArgumentMatchers.any(LocalDateTime.class),
                ArgumentMatchers.any(Pageable.class))).willReturn(pages);

        mvc.perform(get("/api/invoices")
                .param("dateFrom", "2019-05-01 00:00")
                .param("dateTo", "2019-07-01 00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name",
                        is(pages.getContent().get(0).getName())))
                .andExpect(jsonPath("$.content[0].employee.name",
                        is(pages.getContent().get(0).getEmployee().getName())));
    }

    @Test
    public void getInvoice() throws Exception {
        Long invoiceId = invoice.getId();
        given(invoiceService.findById(invoiceId)).willReturn(invoice);

        mvc.perform(get("/api/invoices/{invoiceId}", invoiceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(invoice.getId().intValue())))
                .andExpect(jsonPath("$.name", is(invoice.getName())))
                .andExpect(jsonPath("$.employee.name", is(invoice.getEmployee().getName())));
    }

    @Test
    public void createInvoice() throws Exception {
        given(invoiceService.createFromDTO(ArgumentMatchers.any(InvoiceDTO.class))).willReturn(invoice);

        mvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(invoice.getName())))
                .andExpect(jsonPath("$.employee.name", is(invoice.getEmployee().getName())));
    }

    @Test
    public void updateInvoice() throws Exception {
        Long invoiceId = invoice.getId();
        given(invoiceService.findById(invoiceId)).willReturn(invoice);
        given(invoiceService.updateFromDTO(invoice, invoiceDTO)).willReturn(invoice);

        mvc.perform(put("/api/invoices/{invoiceId}", invoiceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invoiceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(invoice.getId().intValue())))
                .andExpect(jsonPath("$.name", is(invoice.getName())))
                .andExpect(jsonPath("$.employee.name", is(invoice.getEmployee().getName())));
    }

    @Test
    public void deleteInvoice() throws Exception {
        Long invoiceId = invoice.getId();
        given(invoiceService.findById(invoiceId)).willReturn(invoice);

        mvc.perform(delete("/api/invoices/{invoiceId}", invoiceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(invoice.getId().intValue())))
                .andExpect(jsonPath("$.name", is(invoice.getName())))
                .andExpect(jsonPath("$.employee.name", is(invoice.getEmployee().getName())));
    }
}