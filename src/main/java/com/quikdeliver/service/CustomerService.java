package com.quikdeliver.service;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeliverBookingService deliverBookingService;

    private Customer updateCustomer=null;

    //CRUD operations for Customer
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customer,Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.setFirstName(customer.getFirstName());
            c.setLastName(customer.getLastName());
            c.setPhone(customer.getPhone());
            updateCustomer =customerRepository.save(c);
        } );
        return updateCustomer;
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.setDeleted(true);
            customerRepository.save(c);
        } );
    }

    public boolean isCustomerExists(Long id) {
        return customerRepository.existsById(id) && !customerRepository.findById(id).get().isDeleted();
    }

    public boolean isCustomerExists(String nic) {
        return customerRepository.existsCustomerByNic(nic) && !customerRepository.findCustomerByNic(nic).isDeleted();
    }

    //PackageDeliveryRequest operations for Customer

    public PackageDeliveryRequest addDeliverBooking(PackageDeliveryRequest deliverBooking, Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.addDeliverBooking(deliverBooking);
            deliverBookingService.addDeliverBooking(deliverBooking);
            customerRepository.save(c);
        } );
        return deliverBooking;
    }
}
