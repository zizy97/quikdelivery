package com.quikdeliver.service;

import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.model.VehicleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleOwnerServiceTest {
    @Autowired
    VehicleOwnerService vehicleOwnerService;
    @Test
    void getAllVO() {
    }

    @Test
    void getVOById() {
    }

    @Test
    void addVO() {
        VehicleOwner vo = new VehicleOwner();
        vo.setNic("Nadun");
        vo.setPassword("456");
        vo.setEmail("def@gmail.com");
        vo.setPhone("0771234567");
        vehicleOwnerService.addVO(vo);
    }

    @Test
    void updateVO() {
    }

    @Test
    void deleteVO() {
    }

    @Test
    void addVehicleToVO() {
        Vehicle v = new Vehicle();
        v.setType(VehicleType.TWO_TON_VAN);
        v.setModel("Audi");
        vehicleOwnerService.addVehicleToVO(1L,v);
    }

    @Test
    @Transactional
    void updateVehicle() {
        Vehicle vehicleByVO = vehicleOwnerService.getVehicleByVO(1L, 1L);
        vehicleByVO.setModel("KDH");
        vehicleOwnerService.updateVehicle(vehicleByVO,1L,1L);

    }

    @Test
    void deleteVehicle() {
        vehicleOwnerService.deleteVehicle(1L,1L);
    }

    @Test
    @Transactional
    void getVehiclesByVO() {
        Set<Vehicle> vehiclesByVO = vehicleOwnerService.getVehiclesByVO(1L);
        //vehiclesByVO.stream().map(v->v.getVehicleMake()).forEach(System.out::println);

    }

    @Test
    void assignDriver() {
        vehicleOwnerService.assignDriver(1L,3L);
    }

    @Test
    void addDriverToVO() {
        Driver driver = new Driver();
        driver.setNic("Supun");
        driver.setPassword("123");
        driver.setEmail("abc@gmail.com");
        driver.setPhone("0771234567");
        vehicleOwnerService.addDriverToVO(1L,driver);
    }

    @Test
    void assignVehicle() {
        vehicleOwnerService.assignVehicle(1L,1L);
    }
}