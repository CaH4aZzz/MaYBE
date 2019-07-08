package com.maybe.maybe.service;

import com.maybe.maybe.dto.EmployeeDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.entity.enums.converter.UserRoleConverter;
import com.maybe.maybe.repository.EmployeeRepository;
import org.springframework.beans.factory.BeanCreationException;
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

    public Employee findById(Long id) {
        if (employeeRepository.findEmployeeById(id) != null) {
            return employeeRepository.findEmployeeById(id);
        } else {
            throw new EntityNotFoundException("Can not find employee by id = " + id);
        }
    }

    public List<Employee> findAll() {
        if (employeeRepository.findEmployeeById(1L) != null) {
            return employeeRepository.findAll();
        } else {
            throw new EntityNotFoundException("Can not find any employee");
        }
    }

    public Employee createFromDTO(EmployeeDTO employeeDTO) {
        if (employeeRepository.findEmployeeByLogin(employeeDTO.getLogin()) == null) {
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setLogin(employeeDTO.getLogin());
            employee.setPassword(new BCryptPasswordEncoder().encode(employeeDTO.getPassword()));
            employee.setUserRole(new UserRoleConverter().convertToEntityAttribute(employeeDTO.getRoleId()));
            employee.setInvoiceList(null);
            return employeeRepository.save(employee);
        } else {
            throw new BeanCreationException("Can not create entity by dto " + employeeDTO.toString());
        }
    }

    public Employee updateById(Long id, EmployeeDTO employeeRequest) {
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

    public Employee deleteById(Long id){
        if(employeeRepository.findEmployeeById(id) != null){
            Employee employee = employeeRepository.findEmployeeById(id);
            employeeRepository.deleteById(id);
            return employee;
        } else {
            throw new EntityNotFoundException("Can not delete employee by id = " + id);
        }
    }
}
