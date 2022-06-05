package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.repository.CustomerRepository;
import com.quikdeliver.service.CustomerService;
import com.quikdeliver.service.PDRService;
import com.quikdeliver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service @RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PDRService pdrService;


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
        customer.setRoles(Arrays.asList(userService.getRole("ROLE_CUSTOMER")));
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

    @Override
    public PackageDeliveryRequest addRequest(PackageDeliveryRequest request,Long id) {
        return pdrService.addRequest(request,id);
    }

    @Override
    public Set<PackageDeliveryRequest> getRequests(Long customerId) {
        return pdrService.getCustomerRequests(customerId);
    }

}
