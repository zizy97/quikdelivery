package com.quikdeliver.service;

import com.quikdeliver.entity.Driver;
import com.quikdeliver.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepository;

    private Driver updateDriver=null;

    public List<Driver> getAllDriver() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).get();
    }

    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Transactional
    public Driver updateDriver(Driver driver,Long id) {
        driverRepository.findById(id).ifPresent(c -> {
            c.setFirstName(driver.getFirstName());
            c.setLastName(driver.getLastName());
            c.setPhone(driver.getPhone());
            updateDriver =driverRepository.save(c);
        } );
        return updateDriver;
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepository.findById(id).ifPresent(c -> {
            c.setDeleted(true);
            driverRepository.save(c);
        } );
    }

    public boolean isDriverExists(Long id) {
        return driverRepository.existsById(id) && !driverRepository.findById(id).get().isDeleted();
    }

    public boolean isDriverExists(String nic) {
        return driverRepository.existsDriverByNic(nic) && !driverRepository.findDriverByNic(nic).isDeleted();
    }
}
