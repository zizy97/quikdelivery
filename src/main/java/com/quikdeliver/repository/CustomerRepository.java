package com.quikdeliver.repository;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    //Customer findCustomerByNic(String nic);

    boolean existsCustomerByEmail(String email);
    //boolean existsCustomerByNic(String nic);
}
