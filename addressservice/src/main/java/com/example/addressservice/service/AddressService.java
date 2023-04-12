package com.example.addressservice.service;

import com.example.addressservice.entity.Address;
import com.example.addressservice.response.AddressResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    List<AddressResponse> getAddresses();
    Address getAddressById(int addressId);
    AddressResponse getAddressByEmployeeId(int employeeId);

}