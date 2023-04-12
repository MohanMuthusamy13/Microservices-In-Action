package com.example.employee.feignclient;

import com.example.employee.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "address-service", path = "/address-app/api")
public interface AddressClient  {

    @GetMapping("/address/{id}")
    AddressResponse getAddressByEmployeeId(@PathVariable(value = "id") int id);

    @GetMapping("/address")
    List<AddressResponse> getAllAddresses();
}