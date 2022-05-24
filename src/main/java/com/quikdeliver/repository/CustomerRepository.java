package com.quikdeliver.repository;

import com.quikdeliver.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public boolean existsCustomerByNic(String nic);
    public Customer findCustomerByNic(String nic);
}
