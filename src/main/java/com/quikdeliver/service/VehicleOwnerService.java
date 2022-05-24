package com.quikdeliver.service;

import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.repository.VehicleOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class VehicleOwnerService {
    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DeliverBookingService deliverBookingService;
    @Autowired
    private DriverService driverService;

    private VehicleOwner updateVO=null;

    //CRUD Operations for VehicleOwner
    public List<VehicleOwner> getAllVO() {
        return vehicleOwnerRepository.findAll();
    }

    public VehicleOwner getVOById(Long id) {
        return vehicleOwnerRepository.findById(id).get();
    }

    public VehicleOwner addVO(VehicleOwner vo) {
        return vehicleOwnerRepository.save(vo);
    }

    @Transactional
    public VehicleOwner updateVO(VehicleOwner vo,Long id) {
        vehicleOwnerRepository.findById(id).ifPresent(c -> {
            c.setFirstName(vo.getFirstName());
            c.setLastName(vo.getLastName());
            c.setPhone(vo.getPhone());
            updateVO =vehicleOwnerRepository.save(c);
        } );
        return updateVO;
    }

    @Transactional
    public void deleteVO(Long id) {
        vehicleOwnerRepository.findById(id).ifPresent(c -> {
            c.setDeleted(true);
            vehicleOwnerRepository.save(c);
        } );
    }

    public boolean isVOExists(Long id) {
        return vehicleOwnerRepository.existsById(id) && !vehicleOwnerRepository.findById(id).get().isDeleted();
    }

    public boolean isVOExists(String nic) {
        return vehicleOwnerRepository.existsVehicleOwnerByNic(nic) && !vehicleOwnerRepository.findVehicleOwnerByNic(nic).isDeleted();
    }

    //Vehicle added service
    @Transactional
    public Vehicle addVehicleToVO(Long id, Vehicle vehicle) {
        VehicleOwner vehicleOwner = vehicleOwnerRepository.findById(id).get();
        vehicleOwner.addVehicle(vehicle);
        return vehicleService.addVehicle(vehicle);
    }

    public Set<Vehicle> getVehiclesByVO(Long id){
        return vehicleOwnerRepository.findById(id).get().getVehicles();
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
        VehicleOwner vehicleOwner = vehicleOwnerRepository.findById(id).get();
        vehicleOwner.addDriver(driver);
        return driverService.addDriver(driver);
    }

    //PackageDeliveryRequest added service
    @Transactional
    public PackageDeliveryRequest assignDriver(Long deliverBookingId, Long driverId) {
        PackageDeliveryRequest deliverBooking = deliverBookingService.getDeliverBooking(deliverBookingId);
        Driver driverById = driverService.getDriverById(driverId);
        return deliverBookingService.updateDeliverBooking(deliverBooking);
    }

    @Transactional
    public PackageDeliveryRequest assignVehicle(Long deliverBookingId, Long vehicleId) {
        PackageDeliveryRequest deliverBooking = deliverBookingService.getDeliverBooking(deliverBookingId);
        Vehicle vehicleById = vehicleService.getVehicle(vehicleId);
        return deliverBookingService.updateDeliverBooking(deliverBooking);
    }
}
