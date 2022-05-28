package com.quikdeliver.repository;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
//    public boolean existsDriverByNic(String nic);
//    public Driver findDriverByNic(String nic);

    Optional<Driver> findByEmail(String email);
    boolean existsDriverByEmail(String email);
}
