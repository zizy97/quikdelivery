package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.Customer;
import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/customer")
@Slf4j
@RequiredArgsConstructor
@RolesAllowed("ROLE_CUSTOMER")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        log.info("Get all customers");
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        if(customerService.isCustomerExist(id)) {
            log.info("Get customer by id");
            return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
        }else{
            log.error("Customer not found");
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        if(customerService.isCustomerExist(email)) {
            log.info("Get customer by email");
            return new ResponseEntity<>(customerService.getCustomer(email), HttpStatus.OK);
        }else{
            log.error("Customer not found");
            return new ResponseEntity<>(
                    new APIError().addCommonError("Email invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        if(customer.getId() == id) {
            if(customerService.isCustomerExist(id)) {
                log.info("Customer updated");
                customerService.updateCustomer(customer,id);
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
            else{
                log.error("ID invalid or not found");
                return new ResponseEntity<>(
                        new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
            }
        }else{
            log.error("ID Cannot be change");
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID Cannot be change"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}/")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if(customerService.isCustomerExist(id)) {
            customerService.deleteCustomer(id);
            log.info("Customer deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            log.error("ID invalid or not found");
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/delivery-req/{id}")
    public ResponseEntity<?> addRequest(@RequestBody PackageDeliveryRequest packageDeliveryRequest,Long id){
        if(customerService.isCustomerExist(id)) {
            PackageDeliveryRequest addedReq = customerService.addRequest(packageDeliveryRequest, id);
            log.info("Customer deleted");
            return new ResponseEntity<>(addedReq,HttpStatus.CREATED);
        }else{
            log.error("ID invalid or not found");
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }
}
