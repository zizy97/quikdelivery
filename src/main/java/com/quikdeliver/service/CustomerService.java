package com.quikdeliver.service;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.PackageDeliveryRequest;

import java.util.List;
import java.util.Set;

public interface CustomerService {
    //Customer Read services
    public Customer getCustomer(Long id);
    public Customer getCustomer(String email);
    public List<Customer> getAllCustomers();
    public boolean isCustomerExist(Long id);
    public boolean isCustomerExist(String email);

    //Customer create services
    public Customer saveCustomer(Customer customer);

    //Customer update services
    public void updateCustomer(Customer customer,Long id);

    //customer delete services
    public void deleteCustomer(Long id);

    //add Deliver Request
    public PackageDeliveryRequest addRequest(PackageDeliveryRequest request,Customer customer);

    public Set<PackageDeliveryRequest> getRequests(Long customerId);
}
