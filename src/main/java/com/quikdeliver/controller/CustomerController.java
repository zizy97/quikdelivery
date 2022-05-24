package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.Customer;
import com.quikdeliver.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private static  final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        logger.info("Get all customers");
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        if(customerService.isCustomerExists(id)) {
            logger.info("Get customer by id");
            return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
        }else{
            logger.error("Customer not found");
            return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        if(!customerService.isCustomerExists(customer.getNic())) {
            logger.info("Customer added");
            return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
        }else{
            logger.error("Customer Already registered to the system");
            return new ResponseEntity<>(new APIError().addCommonError("Customer Already registered to the system"),HttpStatus.CONFLICT);
        }

    }
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        if(customer.getId() == id) {
            if(customerService.isCustomerExists(id)) {
                logger.info("Customer updated");
                return new ResponseEntity<>(customerService.updateCustomer(customer,id), HttpStatus.OK);
            }
            else{
                logger.error("ID invalid or not found");
                return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
            }
        }else{
            logger.error("ID Cannot be change");
            return new ResponseEntity<>(new APIError().addCommonError("ID Cannot be change"), HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if(customerService.isCustomerExists(id)) {
            customerService.deleteCustomer(id);
            logger.info("Customer deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            logger.error("ID invalid or not found");
            return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }
}
