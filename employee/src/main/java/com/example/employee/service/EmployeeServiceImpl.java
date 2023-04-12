package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.NotFoundException;
import com.example.employee.feignclient.AddressClient;
import com.example.employee.response.AddressResponse;
import com.example.employee.response.EmployeeResponse;
import com.example.employee.respository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private int count = 0;

    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressClient addressClient;

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public EmployeeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    USING REST TEMPLATE TO CONSUME REST SERVICES

//    @Autowired
//    public EmployeeServiceImpl(@Value("${address-service.base.url}") String addressBaseURL,
//                               RestTemplateBuilder builder) {
//        this.restTemplate = builder
//                            .rootUri(addressBaseURL)
//                            .build();
//    }


    @Override
    public EmployeeResponse getEmployeeById(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new NotFoundException("Employee id is invalid")
        );

        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

//        List<ServiceInstance> list = discoveryClient.getInstances("address-service");
//        ServiceInstance serviceInstance = list.get(0);
//        String uri = serviceInstance.getUri().toString();

        // this load balancer takes the corresponding service instances information from
        // the discovery service and then chooses any of the service instances that has less
        // load so that we dispatch the request to appropriate service instances
//        ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
//        String uri = serviceInstance.getUri().toString();
//        String contextPath = serviceInstance.getMetadata().get("context-path");

        AddressResponse addressResponse = getAddressResponseUsingFeignClient(employeeId);
        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;
    }



    @Override
    public List<EmployeeResponse> getEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        employeeList.forEach(
                employee ->
                        employeeResponseList.add(
                                modelMapper.map(employee, EmployeeResponse.class))
        );
        return employeeResponseList;
    }

//  USING FEIGN CLIENT TO CONSUME REST SERVICES
//    @Override
//    public EmployeeResponse getEmployeeById(int employeeId) {
//        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
//                () -> new NotFoundException("Employee id is invalid")
//        );
//        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
//        AddressResponse addressResponse = addressClient.getAddressByEmployeeId(employeeId);
//        employeeResponse.setAddressResponse(addressResponse);
//        return employeeResponse;
//    }

    @Override
    public void saveEmployee(Employee employee) {
        Objects.requireNonNull(employee, "Employee should not be null");
        employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
        Objects.requireNonNull(updatedEmployee, "Employee should not be null");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new NotFoundException("Employee not found")
        );
        employee.setName(updatedEmployee.getName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setBloodGroup(updatedEmployee.getBloodGroup());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(
                () -> new NotFoundException("Employee not found")
        );
        employeeRepository.deleteById(employeeId);
    }

    public AddressResponse getAddressResponseUsingRestTemplate(int employeeId) {
        return restTemplate
                .getForObject("http://address-service/address-app/api/address/{id}",
                        AddressResponse.class, employeeId);
    }

    public AddressResponse getAddressResponseUsingFeignClient(int employeeId) {
        return addressClient.getAddressByEmployeeId(employeeId);
    }

//    @Override
//    public List<AddressResponse> getAllAddressesOfEmployees() {
//        return addressClient.getAllAddresses();
//    }
}