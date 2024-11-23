package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
