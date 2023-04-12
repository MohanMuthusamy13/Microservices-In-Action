package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.response.AddressResponse;
import com.example.employee.response.EmployeeResponse;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable(value = "id") int employeeId) {
        return new ResponseEntity<>(employeeService.getEmployeeById(employeeId),
                HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> saveEmployee(
            @RequestBody Employee employee
    ) {
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable(value = "id") int employeeId,
            @RequestBody Employee employee
    ) {
        return new ResponseEntity<>(employeeService.updateEmployee(employeeId, employee),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(
            @PathVariable(value = "id") int employeeId
    ) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/address")
//    public ResponseEntity<List<AddressResponse>> getAddressOfAllEmployees() {
//        return new ResponseEntity<>(employeeService.getAllAddressesOfEmployees(),
//                HttpStatus.OK);
//    }
}