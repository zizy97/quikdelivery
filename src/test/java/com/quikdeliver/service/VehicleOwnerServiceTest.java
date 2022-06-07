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

@SpringBootTest
class VehicleOwnerServiceTest {
    @Autowired
    com.quikdeliver.service.implimentations.VOServiceImpl VOServiceImpl;
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
        VOServiceImpl.saveVO(vo);
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
//        VOServiceImpl.addVehicleToVO(1L,v);
    }

    @Test
    @Transactional
    void updateVehicle() {
        Vehicle vehicleByVO = VOServiceImpl.getVehicleByVO(1L, 1L);
        vehicleByVO.setModel("KDH");
        VOServiceImpl.updateVehicle(vehicleByVO,1L,1L);

    }

    @Test
    void deleteVehicle() {
        VOServiceImpl.deleteVehicle(1L,1L);
    }

    @Test
    @Transactional
    void getVehiclesByVO() {
//        Set<Vehicle> vehiclesByVO = VOServiceImpl.getVehiclesByVO(1L);
        //vehiclesByVO.stream().map(v->v.getVehicleMake()).forEach(System.out::println);

    }

    @Test
    void assignDriver() {
        VOServiceImpl.assignDriver(1L,3L);
    }

    @Test
    void addDriverToVO() {
        Driver driver = new Driver();
        driver.setNic("Supun");
        driver.setPassword("123");
        driver.setEmail("abc@gmail.com");
//        VOServiceImpl.addDriverToVO(1L,driver);
    }

    @Test
    void assignVehicle() {
        VOServiceImpl.assignVehicle(1L,1L);
    }
}