package com.maybe.maybe.service;

import com.maybe.maybe.dto.EmployeeRequest;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private  Employee expectedEmployee;

    @Before
    public void setUp(){
        employeeService = new EmployeeService(employeeRepository);
        expectedEmployee = new Employee();
        expectedEmployee.setName("name1");
        expectedEmployee.setLogin("login1");
        expectedEmployee.setPassword(new BCryptPasswordEncoder().encode("password"));
        expectedEmployee.setUserRole(UserRole.USER);
        expectedEmployee.setInvoiceList(null);
    }

    @Test
    public void getEmployeeById() {
        Long id = 1L;
        when(employeeRepository.findEmployeeById(id)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.getEmployeeById(id);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test
    public void getEmployeeList() {
        List<Employee> expectedEmployeeList = new ArrayList<>();
        expectedEmployeeList.add(expectedEmployee);
        when(employeeRepository.findAll()).thenReturn(expectedEmployeeList);

        List<Employee> actualEmployeeList = employeeService.getEmployeeList();

        assertEquals(expectedEmployeeList.get(0).getLogin(),actualEmployeeList.get(0).getLogin());
    }

    @Test
    public void createEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest("name1","login1",
                "password",2);
        when(employeeRepository.save(expectedEmployee)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.createEmployee(employeeRequest);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test
    public void updateEmployeeById() {
        Long id = 1L;
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "name1","login1", "password1",1
        );
        when(employeeRepository.findEmployeeById(id)).thenReturn(expectedEmployee);
        when(employeeRepository.save(expectedEmployee)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.updateEmployeeById(id,employeeRequest);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }
}