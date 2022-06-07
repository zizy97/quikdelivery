package com.quikdeliver.service;

import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.entity.Vehicle;
import com.quikdeliver.entity.VehicleOwner;

import java.util.List;
import java.util.Set;

public interface VOService {
    //VehicleOwner Read services
    public VehicleOwner getVO(Long id);

    public VehicleOwner getVO(String email);

    public List<VehicleOwner> getVOs();

    public boolean isVOExist(Long id);

    public boolean isVOExist(String email);

    //VehicleOwner create services
    public VehicleOwner saveVO(VehicleOwner driver);

    //VehicleOwner update services
    public void updateVO(VehicleOwner user, Long id);

    //customer delete services
    public void deleteVO(Long id);


    //Vehicle Related Services
    public Vehicle addVehicleToVO(String email, Vehicle vehicle);
    public List<Vehicle> getVehiclesByVO(String email);
    public List<Vehicle> getVehiclesByVO(Long id);
    public Vehicle getVehicleByVO(Long id, Long vehicleId);
    public void updateVehicle(Vehicle vehicle, Long voId, Long vehicleId);
    public void deleteVehicle(Long voId, Long vehicleId);

    //Driver Related Services



    //Package deliver request
    public void assignAllocation(Long pdrId, Long vehicleId, Long driverId);

    public void assignVehicle(Long pdrId, Long vehicleId);

    public void assignDriver(Long pdrId, Long driverId);
}
