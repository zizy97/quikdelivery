package com.quikdeliver.service;

import com.quikdeliver.entity.Driver;

import java.util.List;

public interface DriverService {
    //Driver Read services
    public Driver getDriver(Long id);
    public Driver getDriver(String email);
    public List<Driver> getAllDrivers();
    public boolean isDriverExist(Long id);
    public boolean isDriverExist(String email);

    //Driver create services
    public Driver saveDriver(Driver driver);

    //Driver update services
    public void updateDriver(Driver user,Long id);

    //customer delete services
    public void deleteDriver(Long id);
}
