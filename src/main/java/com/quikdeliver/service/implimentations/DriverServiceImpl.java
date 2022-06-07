package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.Allocation;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.repository.AllocationRepository;
import com.quikdeliver.repository.DriverRepository;
import com.quikdeliver.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class DriverServiceImpl  implements DriverService {

    private final PasswordEncoder passwordEncoder;
    private final DriverRepository driverRepository;
    private final AllocationRepository allocationRepository;

    @Override
    public Driver getDriver(Long id) {
        return driverRepository.findById(id).get();
    }

    @Override
    public Driver getDriver(String email) {
        return driverRepository.findByEmail(email).get();
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public boolean isDriverExist(Long id) {
        return driverRepository
                .existsById(id) && !this.getDriver(id).isDeleted();
    }

    @Override
    public boolean isDriverExist(String email) {
        return driverRepository
                .existsDriverByEmail(email) && !this.getDriver(email).isDeleted();
    }

    @Override
    public Driver saveDriver(Driver driver) {
        driver.setPassword(passwordEncoder
                .encode(driver.getPassword()));
        return driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void updateDriver(Driver driver, Long id) {
        driverRepository.findById(id).ifPresent(c -> {
            c.setName(driver.getName());
        });
    }

    @Override
    @Transactional
    public void deleteDriver(Long id) {
        driverRepository.findById(id).ifPresent(c -> {
            c.setDeleted(true);
        } );
    }

    @Override @Transactional
    public List<Allocation> getAllocation(String email) {
        Driver driver = getDriver(email);
        return driver.getAllocations();
    }

    @Override
    public void acceptAllocation(Driver driver,Long allocationId) {
        driver.getAllocations().stream().map(Allocation::getPackageDeliveryRequest).forEach(p->{

        });
    }
}
