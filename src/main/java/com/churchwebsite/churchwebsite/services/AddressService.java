package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> findById(int id){
        return addressRepository.findById(id);
    }

    public Address save(Address address){
        return addressRepository.save(address);
    }

}
