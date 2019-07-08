package com.maybe.maybe.controller;

import com.maybe.maybe.annotation.Statistic;
import com.maybe.maybe.dto.EmployeeDTO;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Employee> getEmployById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.findById(id));
    }

    @Statistic
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployeeList() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.findAll());
    }

    @Statistic
    @PostMapping("/employees")
    private ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createFromDTO(employeeDTO));
    }

    @Statistic
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable("id") Long id,
                                                       @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.updateById(id, employeeDTO));
    }

    @Statistic
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.deleteById(id));
    }

}
