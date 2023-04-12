package com.example.addressservice.respository;

import com.example.addressservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "SELECT eas.id, eas.lane1, eas.lane2, eas.state, eas.zip\n" +
            "FROM employee_service es\n" +
            "LEFT JOIN address_service eas\n" +
            "ON es.id = eas.employeeId\n" +
            "where eas.employeeId = ?1", nativeQuery = true)
    Address findByEmployeeId(long employeeId);
}