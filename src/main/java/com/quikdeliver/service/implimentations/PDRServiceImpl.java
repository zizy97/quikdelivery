package com.quikdeliver.service.implimentations;

import com.quikdeliver.entity.*;
import com.quikdeliver.repository.CustomerRepository;
import com.quikdeliver.repository.PDRRepository;
import com.quikdeliver.repository.DriverRepository;
import com.quikdeliver.repository.VehicleRepository;
import com.quikdeliver.service.PDRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor @Slf4j
@Service
public class PDRServiceImpl implements PDRService {

    private final PDRRepository pdrRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public Set<PackageDeliveryRequest> getCustomerRequests(Long customerId) {
        Set<PackageDeliveryRequest> packageDeliveryRequests = new HashSet<>();
        customerRepository.findById(customerId).get().getDeliverBookings().forEach(request -> {
            if(!request.isDeleted())
                packageDeliveryRequests.add(request);
        });
        return packageDeliveryRequests;
    }

    @Override
    public Set<PackageDeliveryRequest> getDriverRequests(Long driverId) {
        Set<PackageDeliveryRequest> packageDeliveryRequests = new HashSet<>();
        driverRepository.findById(driverId).get().getAllocations().stream().map(Allocation::getPackageDeliveryRequest).forEach(request -> {
            if(!request.isDeleted())
                packageDeliveryRequests.add(request);
        });
        return packageDeliveryRequests;
    }

    @Override
    public Set<PackageDeliveryRequest> getVehicleRequests(Long vehicleId) {
        Set<PackageDeliveryRequest> packageDeliveryRequests = new HashSet<>();
        vehicleRepository.findById(vehicleId).get().getAllocations().stream().map(Allocation::getPackageDeliveryRequest).forEach(request -> {
            if(!request.isDeleted())
                packageDeliveryRequests.add(request);
        });
        return packageDeliveryRequests;
    }

    @Override
    public PackageDeliveryRequest addRequest(PackageDeliveryRequest request, Customer customer) {
//        Customer customer = customerRepository.findById(customerId).get();
        request.setCustomer(customer);
        return pdrRepository.save(request);
    }

    @Override
    public void updateRequest(PackageDeliveryRequest request) {
        pdrRepository.save(request);
    }

    @Override @Transactional
    public void deleteRequest(Long id) {
        PackageDeliveryRequest packageDeliveryRequest = pdrRepository.findById(id).get();
        packageDeliveryRequest.setDeleted(true);
    }

    @Override @Transactional
    public void addAllocation(Allocation allocation,Long id) {
        PackageDeliveryRequest packageDeliveryRequest = pdrRepository.findById(id).get();
        packageDeliveryRequest.setAllocation(allocation);
    }

    @Override
    public void updateDriver(Long driverId,Long reqId) {
        Driver driver = driverRepository.findById(driverId).get();
        PackageDeliveryRequest packageDeliveryRequest = pdrRepository.findById(reqId).get();
        packageDeliveryRequest.getAllocation().setDriver(driver);
    }

    @Override
    public void updateVehicle(Long vehicleId,Long reqId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
        PackageDeliveryRequest packageDeliveryRequest = pdrRepository.findById(reqId).get();
        packageDeliveryRequest.getAllocation().setVehicle(vehicle);
    }

    @Override
    public void deletePermanent() {
        List<PackageDeliveryRequest> all = pdrRepository.findAll();
        all.forEach(p->{
            if(p.isDeleted()){
                pdrRepository.delete(p);
            }
        });
    }
}
