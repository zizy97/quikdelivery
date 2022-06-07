package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.*;
import com.quikdeliver.repository.VORepository;
import com.quikdeliver.repository.VehicleRepository;
import com.quikdeliver.service.DriverService;
import com.quikdeliver.service.PDRService;
import com.quikdeliver.service.VOService;
import com.quikdeliver.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VOServiceImpl implements VOService {
    private final VORepository voRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final PDRService pdrService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public VehicleOwner getVO(Long id) {
        return voRepository.findById(id).get();
    }

    @Override
    public VehicleOwner getVO(String email) {
        return  voRepository.findByEmail(email).get();
    }

    @Override
    public List<VehicleOwner> getVOs() {
        return voRepository.findAll();
    }

    @Override
    public boolean isVOExist(Long id) {
        return voRepository.existsById(id) && !getVO(id).isDeleted();
    }

    @Override
    public boolean isVOExist(String email) {
        return voRepository.existsVehicleOwnerByEmail(email) && !getVO(email).isDeleted();
    }

    @Override
    public VehicleOwner saveVO(VehicleOwner vehicleOwner) {
        vehicleOwner.setPassword(
                passwordEncoder.encode(vehicleOwner.getPassword()));
        return voRepository.save(vehicleOwner);
    }

    @Transactional
    public void updateVO(VehicleOwner vo,Long id) {
        voRepository.findById(id).ifPresent(c -> {
            c.setName(vo.getName());
            voRepository.save(c);
        });
    }

    @Transactional
    public void deleteVO(Long id) {
        voRepository.findById(id).ifPresent(c -> {
            c.setDeleted(true);
            voRepository.save(c);
        } );
    }


    //Vehicle added service
    @Override @Transactional
    public Vehicle addVehicleToVO(String email, Vehicle vehicle) {
        VehicleOwner vehicleOwner = getVO(email);
        vehicleOwner.addVehicle(vehicle);
        Vehicle addedVehicle = vehicleService.addVehicle(vehicle);
        return addedVehicle;
    }

    //get vehicle by user email
    @Override @Transactional
    public List<Vehicle> getVehiclesByVO(String email){
        VehicleOwner vehicleOwner = voRepository.findByEmail(email).get();
        List<Vehicle> vehicles = vehicleOwner.getVehicles();
        return vehicles;
    }

    //get vehicle by user id from vehicle repo
    @Override
    public List<Vehicle> getVehiclesByVO(Long id){
        return vehicleService.getVehicles(id);
    }

    @Override
    public Vehicle getVehicleByVO(Long id, Long vehicleId){
        return vehicleService.getVehicle(id,vehicleId);
    }

    @Override
    public void updateVehicle(Vehicle vehicle, Long voId, Long vehicleId) {
        vehicleService.updateVehicle(voId,vehicleId,vehicle);
    }

    @Override
    public void deleteVehicle(Long voId, Long vehicleId) {
        vehicleService.deleteVehicle(voId,vehicleId);
    }

    //Driver added service
    public Driver addDriverToVO(String email, Driver driver) {
        VehicleOwner vehicleOwner = voRepository.findByEmail(email).get();
        vehicleOwner.addDriver(driver);
        return driverService.saveDriver(driver);
    }

    //PackageDeliveryRequest added service
    @Override
    public void assignAllocation(Long pdrId, Long vehicleId, Long driverId) {
        Allocation allocation=new Allocation();
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        Driver driver = driverService.getDriver(driverId);
        allocation.setVehicle(vehicle);
        allocation.setDriver(driver);
        pdrService.addAllocation(allocation,pdrId);
    }

    public void assignDriver(Long pdrId, Long driverId) {
        pdrService.updateDriver(driverId,pdrId);
    }

    public void assignVehicle(Long pdrId, Long vehicleId) {
        pdrService.updateVehicle(vehicleId,pdrId);
    }
}
