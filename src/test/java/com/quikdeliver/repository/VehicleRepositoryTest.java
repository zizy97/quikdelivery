package com.quikdeliver.repository;

import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VORepository voRepository;

    @Test
    void save(){
        vehicleRepository.findAll().stream().map(Vehicle::getVehicleOwner).map(VehicleOwner::getId).forEach(System.out::println);
    }
}