package com.quikdeliver.service;

import com.quikdeliver.entity.*;

import java.util.Set;

public interface PDRService {

    //Get Package Delivery Requests
    public Set<PackageDeliveryRequest> getCustomerRequests(Long customerId);

    public Set<PackageDeliveryRequest> getDriverRequests(Long driverId);

    public Set<PackageDeliveryRequest> getVehicleRequests(Long vehicleId);

    //Add Delivery Requests
    public PackageDeliveryRequest addRequest(PackageDeliveryRequest request, Long customerId);

    //Update Delivery Requests
    public void updateRequest(PackageDeliveryRequest request);

    //Delete Delivery Requests
    public void deleteRequest(Long id);


    //Add Allocations
    public void addAllocation(Allocation allocation,Long id);

    //Update Driver
    public void updateDriver(Long driverId,Long reqId);

    //Update Vehicle
    public void updateVehicle(Long vehicleId,Long reqId);
}
