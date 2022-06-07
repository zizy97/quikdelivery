package com.quikdeliver.service;

import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;
import com.quikdeliver.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle getVehicle(Long id) {
        return vehicleRepository.findById(id).get();
    }

    public List<Vehicle> getVehicles(Long voId){
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleRepository.findAll().forEach(v->{
            if(v.getVehicleOwner().getId()==voId){
                vehicleList.add(v);
            }
        });
        vehicleList.stream().map(Vehicle::getVehicleOwner).map(VehicleOwner::getId).forEach(System.out::println);
        return vehicleList;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void updateVehicle(Long voId,Long vehicleId, Vehicle vehicle) {
         vehicleRepository.updateVehicle(vehicle,voId,vehicleId);
    }

    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicle(Long voId,Long vehicleId) {
        return vehicleRepository.getVehicle(voId,vehicleId);
    }

    public void deleteVehicle(Long voId, Long vehicleId) {
        Vehicle vehicle = getVehicle(voId, vehicleId);
        vehicle.setDeleted(true);
        updateVehicle(vehicle);
    }
}
