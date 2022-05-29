package com.quikdeliver.repository;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    //Customer findCustomerByNic(String nic);

    boolean existsCustomerByEmail(String email);
    //boolean existsCustomerByNic(String nic);

    @Modifying
    @Query(value = "insert into customers (id) VALUES (:id)", nativeQuery = true)
    @Transactional
    void updateNewRecord( @Param("id") Long id);
}
