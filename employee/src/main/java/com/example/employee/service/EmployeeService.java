package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.response.AddressResponse;
import com.example.employee.response.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<EmployeeResponse> getEmployees();
    EmployeeResponse getEmployeeById(int employeeId);
    void saveEmployee(Employee employee);
    Employee updateEmployee(int employeeId, Employee employee);
    void deleteEmployee(int employeeId);
//    List<AddressResponse> getAllAddressesOfEmployees();
}