package com.maybe.maybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybe.maybe.dto.EmployeeDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Employee employee;

    private EmployeeDTO employeeDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employee = new Employee();
        employee.setId(1L);
        employee.setName("name");
        employee.setLogin("login");
        employee.setPassword("password");
        employee.setInvoiceList(null);
        employee.setUserRole(UserRole.USER);
        employeeDTO = new EmployeeDTO("name", "login", "password", 2);
    }

    @Test
    public void getEmployById() throws Exception {
        Long id = 1L;
        when(employeeService.findById(id)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeList() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeService.findAll()).thenReturn(employeeList);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk());
    }

    @Test
    public void createEmployeeTest() throws Exception {
        when(employeeService.createFromDTO(employeeDTO)).thenReturn(employee);
        EmployeeDTO employeeDTO = new EmployeeDTO("name", "login", "password", 2);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated());

    }

    @Test
    public void updateEmployeeById() throws Exception {
        Long id = 1L;
        when(employeeService.updateById(id, employeeDTO)).thenReturn(employee);

        mockMvc.perform(put("/api/employees/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeById() throws Exception {
        Long id = 1L;
        when(employeeService.deleteById(id)).thenReturn(employee);

        mockMvc.perform(delete("/api/employees/" + id))
                .andExpect(status().isOk());
    }
}