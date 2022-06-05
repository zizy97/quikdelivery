package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.repository.VORepository;
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
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class VOServiceImpl implements VOService {
    private final VORepository voRepository;
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
    @Transactional
    public Vehicle addVehicleToVO(Long id, Vehicle vehicle) {
        VehicleOwner vehicleOwner = voRepository.findById(id).get();
        vehicleOwner.addVehicle(vehicle);
        return vehicleService.addVehicle(vehicle);
    }

    public Set<Vehicle> getVehiclesByVO(Long id){
        return voRepository.findById(id).get().getVehicles();
    }

    public Vehicle getVehicleByVO(Long id, Long vehicleId){
        return vehicleService.getVehicle(id,vehicleId);
    }

    public void updateVehicle(Vehicle vehicle, Long voId, Long vehicleId) {
        vehicleService.updateVehicle(voId,vehicleId,vehicle);
    }
    public void deleteVehicle(Long voId, Long vehicleId) {
        vehicleService.deleteVehicle(voId,vehicleId);
    }

    //Driver added service

    public Driver addDriverToVO(Long id, Driver driver) {
        VehicleOwner vehicleOwner = voRepository.findById(id).get();
        vehicleOwner.addDriver(driver);
        return driverService.saveDriver(driver);
    }

    //PackageDeliveryRequest added service
    public void assignDriver(Long deliverBookingId, Long driverId) {
        pdrService.updateDriver(driverId,deliverBookingId);
    }

    public void assignVehicle(Long deliverBookingId, Long vehicleId) {
        pdrService.updateVehicle(vehicleId,deliverBookingId);
    }
}
