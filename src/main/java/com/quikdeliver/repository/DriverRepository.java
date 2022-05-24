package com.quikdeliver.repository;

import com.quikdeliver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    public boolean existsDriverByNic(String nic);
    public Driver findDriverByNic(String nic);
}
