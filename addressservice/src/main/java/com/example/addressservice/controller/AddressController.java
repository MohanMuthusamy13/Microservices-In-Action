package com.example.addressservice.controller;

import com.example.addressservice.response.AddressResponse;
import com.example.addressservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddress() {
        return new ResponseEntity<>(addressService.getAddresses(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public AddressResponse getAddressByEmployeeId(
            @PathVariable(value = "id") int employeeId
    ) {
        return addressService.getAddressByEmployeeId(employeeId);
    }


}