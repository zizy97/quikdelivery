package com.quikdeliver.controller;

import com.quikdeliver.advice.exception.APIError;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDriver(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable Long id) {
        if(driverService.isDriverExists(id)) {
            return new ResponseEntity<>(driverService.getDriverById(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        if(!driverService.isDriverExists(driver.getNic())) {
            return new ResponseEntity<>(driverService.addDriver(driver), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("Driver Already registered to the system"),HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateDriver(@RequestBody Driver driver, @PathVariable Long id) {
        if(driver.getId() == id) {
            if(driverService.isDriverExists(id)) {
                return new ResponseEntity<>(driverService.updateDriver(driver,id), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("ID Cannot be change"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
        if(driverService.isDriverExists(id)) {
            driverService.deleteDriver(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new APIError().addCommonError("ID invalid or not found"),HttpStatus.NOT_FOUND);
        }
    }
}
