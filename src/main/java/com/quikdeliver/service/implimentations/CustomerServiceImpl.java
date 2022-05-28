package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.repository.CustomerRepository;
import com.quikdeliver.service.CustomerService;
import com.quikdeliver.service.DeliverBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final DeliverBookingService deliverBookingService;

    public PackageDeliveryRequest addDeliverBooking(PackageDeliveryRequest deliverBooking, Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.addDeliverBooking(deliverBooking);
            deliverBookingService.addDeliverBooking(deliverBooking);
            customerRepository.save(c);
        } );
        return deliverBooking;
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer getCustomer(String email) {
        return customerRepository.findByEmail(email).get();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean isCustomerExist(Long id) {
        return customerRepository
                .existsById(id) && !this.getCustomer(id).isDeleted();
    }

    @Override
    public boolean isCustomerExist(String email) {
        return customerRepository
                .existsCustomerByEmail(email) && !this.getCustomer(email).isDeleted();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer,Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.setName(customer.getName());
            customerRepository.save(c);
        });
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.setDeleted(true);
            customerRepository.save(c);
        } );
    }
}
