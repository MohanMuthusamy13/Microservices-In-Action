package com.example.addressservice.service;

import com.example.addressservice.entity.Address;
import com.example.addressservice.exception.NotFoundException;
import com.example.addressservice.response.AddressResponse;
import com.example.addressservice.respository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressResponse> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressResponse> responses = new ArrayList<>();
        addresses.forEach(address -> responses.add(modelMapper.map(address, AddressResponse.class)));
        return responses;
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressRepository.findById(addressId).orElseThrow(
                () ->  new NotFoundException("Address Id is invalid")
        );
    }

    @Override
    public AddressResponse getAddressByEmployeeId(int employeeId) {
        Address address = Objects.requireNonNull(
                addressRepository.findByEmployeeId(employeeId),
                "Address is not found for employee"
                );

        return modelMapper.map(address, AddressResponse.class);
    }
}