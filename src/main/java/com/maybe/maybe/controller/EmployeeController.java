package com.maybe.maybe.controller;

import com.maybe.maybe.dto.EmployeeRequest;
import com.maybe.maybe.entity.Employee;
import com.maybe.maybe.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(employeeService.getEmployeeById(id));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployeeList(){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(employeeService.getEmployeeList());
    }

    @PostMapping("/employee")
    private ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(employeeService.createEmployee(employeeRequest));
    }

    @PostMapping("/employees")
    public ResponseEntity<List<Employee>> createEmployeeList(@Valid @RequestBody List<EmployeeRequest> employeeRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(employeeService.createEmployeeList(employeeRequest));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id,
                                                   @RequestBody EmployeeRequest employeeRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(employeeService.updateEmployeeById(id,employeeRequest));
    }

}
