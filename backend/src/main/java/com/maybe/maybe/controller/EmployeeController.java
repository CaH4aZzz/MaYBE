package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.EmployeeDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Statistic
    @GetMapping("/employees/{id}")
    public Employee getEmployById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @Statistic
    @GetMapping("/employees")
    public List<Employee> getEmployeeList() {
        return employeeService.findAll();
    }

    @Statistic
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createFromDTO(employeeDTO);
    }

    @Statistic
    @PutMapping("/employees/{id}")
    public Employee updateEmployeeById(@PathVariable("id") Long id,
                                                       @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateById(id, employeeDTO);
    }

    @Statistic
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable @Min(1) Long id) {
        employeeService.deleteById(id);
    }

}
