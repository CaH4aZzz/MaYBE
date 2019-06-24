package com.maybe.maybe.service;

import com.maybe.maybe.dto.EmployeeRequest;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
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
        employeeRepository.save(expectedEmployee);

        Employee actualEmployee = employeeRepository.findEmployeeById(id);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test
    public void getEmployeeList() {
        List<Employee> expectedEmployeeList = new ArrayList<>();
        expectedEmployeeList.add(expectedEmployee);
        employeeRepository.saveAll(expectedEmployeeList);

        List<Employee> actualEmployee = employeeService.getEmployeeList();

        assertEquals(expectedEmployeeList.get(0).getLogin(),actualEmployee.get(0).getLogin());
    }

    @Test
    public void createEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest("name1","login1",
                "password",2);
        Employee expectedEmployee = new Employee();
        expectedEmployee.setName("name1");
        expectedEmployee.setLogin("login1");
        expectedEmployee.setPassword(new BCryptPasswordEncoder().encode("password"));
        expectedEmployee.setUserRole(UserRole.USER);
        expectedEmployee.setInvoiceList(null);
        //employeeRepository.save(expectedEmployee);

        System.out.println(employeeService.createEmployee(employeeRequest));
        Employee actualEmployee = employeeService.createEmployee(employeeRequest);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test
    public void createEmployeeList() {
        List<EmployeeRequest> employeeRequestList = new ArrayList<>();
        EmployeeRequest employeeRequestOne = new EmployeeRequest("name1","login1","password1",1);
        EmployeeRequest employeeRequestTwo = new EmployeeRequest("name2","login2","password2",1);
        employeeRequestList.add(employeeRequestOne);
        employeeRequestList.add(employeeRequestTwo);

        List<Employee> actualEmployeeList = employeeService.createEmployeeList(employeeRequestList);

        assertEquals(employeeRequestList.size(),actualEmployeeList.size());
        assertEquals(employeeRequestList.get(0).getLogin(),actualEmployeeList.get(0).getLogin());
    }

    @Test
    public void updateEmployeeById() {
        Long id = 1L;
        EmployeeRequest employeeRequestOne = new EmployeeRequest("name1","login1","password1",1);
        employeeRepository.save(expectedEmployee);

        Employee actualEmployee = employeeService.updateEmployeeById(id,employeeRequestOne);

        assertEquals(employeeRequestOne.getLogin(),actualEmployee.getLogin());
    }
}