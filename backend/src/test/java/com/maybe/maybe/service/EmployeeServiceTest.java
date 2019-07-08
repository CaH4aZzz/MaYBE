package com.maybe.maybe.service;

import com.maybe.maybe.dto.EmployeeDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private  Employee expectedEmployee;

    private  Long id;

    private EmployeeDTO employeeDTO;

    @Before
    public void setUp(){
        employeeService = new EmployeeService(employeeRepository);
        expectedEmployee = new Employee();
        expectedEmployee.setName("name1");
        expectedEmployee.setLogin("login1");
        expectedEmployee.setPassword(new BCryptPasswordEncoder().encode("password"));
        expectedEmployee.setUserRole(UserRole.USER);
        expectedEmployee.setInvoiceList(null);
        id = 1L;
        employeeDTO = new EmployeeDTO(
                "name1","login1", "password1",1
        );
    }

    @Test
    public void findByIdTest() {
        when(employeeRepository.findEmployeeById(id)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.findById(id);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findById_thenReturnException(){
        when(employeeRepository.findEmployeeById(id)).thenReturn(null);

        employeeService.findById(id);
    }

    @Test
    public void findAllTest() {
        List<Employee> expectedEmployeeList = new ArrayList<>();
        expectedEmployeeList.add(expectedEmployee);
        when(employeeRepository.findEmployeeById(id)).thenReturn(expectedEmployee);
        when(employeeRepository.findAll()).thenReturn(expectedEmployeeList);

        List<Employee> actualEmployeeList = employeeService.findAll();

        assertArrayEquals(expectedEmployeeList.toArray(),actualEmployeeList.toArray());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findAll_thenReturnException() {
        when(employeeRepository.findEmployeeById(id)).thenReturn(null);

        employeeService.findAll();
    }

    @Test
    public void createFromDTOTest() {
        when(employeeRepository.findEmployeeByLogin(expectedEmployee.getLogin())).thenReturn(null);
        when(employeeRepository.save(expectedEmployee)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.createFromDTO(employeeDTO);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test(expected = BeanCreationException.class)
    public void createFromDTO_thenReturnException() {
        when(employeeRepository.findEmployeeByLogin(expectedEmployee.getLogin())).thenReturn(expectedEmployee);

        employeeService.createFromDTO(employeeDTO);
    }

    @Test
    public void updateByIdTest() {
        when(employeeRepository.findEmployeeById(id)).thenReturn(expectedEmployee);
        when(employeeRepository.save(expectedEmployee)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.updateById(id,employeeDTO);

        assertEquals(expectedEmployee.getLogin(),actualEmployee.getLogin());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateById_thenReturnException() {
        when(employeeRepository.findEmployeeById(id)).thenReturn(null);

        employeeService.updateById(id,employeeDTO);
    }

    @Test
    public void deleteById() {
        when(employeeRepository.findEmployeeById(id)).thenReturn(expectedEmployee);

        Employee actualEmployee = employeeService.deleteById(id);

        assertEquals(expectedEmployee,actualEmployee);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteById_thenReturnException() {
        when(employeeRepository.findEmployeeById(id)).thenReturn(null);

        employeeService.deleteById(id);
    }
}