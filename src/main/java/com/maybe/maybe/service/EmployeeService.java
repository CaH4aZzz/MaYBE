package com.maybe.maybe.service;

import com.maybe.maybe.dto.EmployeeRequest;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.enums.converter.UserRoleConverter;
import com.maybe.maybe.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployeeById(Long id) {
        if (employeeRepository.findEmployeeById(id) != null) {
            return employeeRepository.findEmployeeById(id);
        } else {
            throw new EntityNotFoundException("Can not find employee by id = " + id);
        }
    }

    public List<Employee> getEmployeeList() {
        if (!employeeRepository.findAll().isEmpty()) {
            return employeeRepository.findAll();
        } else {
            throw new EntityNotFoundException("Can not find any employee");
        }
    }

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        if (employeeRepository.findEmployeeByLogin(employeeRequest.getLogin()) == null) {
            Employee employee = new Employee();
            employee.setName(employeeRequest.getName());
            employee.setLogin(employeeRequest.getLogin());
            employee.setPassword(new BCryptPasswordEncoder().encode(employeeRequest.getPassword()));
            employee.setUserRole(new UserRoleConverter().convertToEntityAttribute(employeeRequest.getRoleId()));
            employee.setInvoiceList(null);
            return employeeRepository.save(employee);
        }
        return null;
    }

    public Employee updateEmployeeById(Long id, EmployeeRequest employeeRequest) {
        if (employeeRepository.findEmployeeById(id) != null) {
            Employee employee = employeeRepository.findEmployeeById(id);
            employee.setName(employeeRequest.getName());
            employee.setLogin(employeeRequest.getLogin());
            employee.setPassword(new BCryptPasswordEncoder().encode(employeeRequest.getPassword()));
            employee.setUserRole(new UserRoleConverter().convertToEntityAttribute(employeeRequest.getRoleId()));
            employee.setInvoiceList(null);
            employee.setId(id);
            return employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException("Can not update employee by id = " + id);
        }
    }
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find employee id=" + id));
    }
}
