package com.quikdeliver.repository;

import com.quikdeliver.entity.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Long> {
    public boolean existsVehicleOwnerByNic(String nic);
    public VehicleOwner findVehicleOwnerByNic(String nic);
}
