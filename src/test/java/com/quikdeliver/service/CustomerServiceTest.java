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
        customerService.getAllCustomers().stream().forEach(e->System.out.println(e.getId()));
    }

    @Test
    void getCustomerById() {
        System.out.println(customerService.getCustomer(1L).getId());
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer();
        customer.setNic("Supun");
        customer.setPassword("123");
        customer.setEmail("abc@gmail.com");

        customerService.saveCustomer(customer);
    }

    @Test
    void updateCustomer() {
        Customer customer = customerService.getCustomer(1L);
        customer.setName("Supun");
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
        //customerService.addDeliverBooking(packageDeliveryRequest, 2L);
    }
}