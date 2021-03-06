package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.service.DriverService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/driver")
@RequiredArgsConstructor
@RolesAllowed({"ROLE_DRIVER","ROLE_VO"})
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable Long id) {
        if(driverService.isDriverExist(id)) {
            return new ResponseEntity<>(driverService.getDriver(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@RequestBody Driver driver, @PathVariable Long id) {
        if(driver.getId() == id) {
            if(driverService.isDriverExist(id)) {
                driverService.updateDriver(driver,id);
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(
                        new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID Cannot be change"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
        if(driverService.isDriverExist(id)) {
            driverService.deleteDriver(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/accept-req/{allocationId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Long allocationId){

        return null;
    }

    @GetMapping("allocations")
    public ResponseEntity<?> getAllAllocations(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
