package com.quikdeliver.repository;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
//    public boolean existsDriverByNic(String nic);
//    public Driver findDriverByNic(String nic);

    Optional<Driver> findByEmail(String email);
    boolean existsDriverByEmail(String email);

    @Modifying
    @Query(value = "insert into drivers (id) VALUES (:id)", nativeQuery = true)
    @Transactional
    void updateNewRecord( @Param("id") Long id);
}
