package com.quikdeliver.service;

import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.PackageDeliveryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void getAllCustomer() {
        customerService.getAllCustomer().stream().forEach(e->System.out.println(e.getId()));
    }

    @Test
    void getCustomerById() {
        System.out.println(customerService.getCustomerById(1L).getId());
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer();
        customer.setNic("Supun");
        customer.setPassword("123");
        customer.setEmail("abc@gmail.com");
        customer.setPhone("0771234567");

        customerService.addCustomer(customer);
    }

    @Test
    void updateCustomer() {
        Customer customer = customerService.getCustomerById(1L);
        customer.setFirstName("Supun");
        customer.setLastName("Wijegunawardhana");
        customerService.updateCustomer(customer, 1L);
    }

    @Test
    void deleteCustomer() {
        customerService.deleteCustomer(1L);
    }

    @Test
    void addDeliverBooking() {
        PackageDeliveryRequest packageDeliveryRequest = new PackageDeliveryRequest();
        packageDeliveryRequest.setPickupAddress("Kandy");
        packageDeliveryRequest.setDropOffAddress("Colombo");

        customerService.addDeliverBooking(packageDeliveryRequest, 2L);
    }
}