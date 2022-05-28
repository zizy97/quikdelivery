package com.quikdeliver.repository;

import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VORepository extends JpaRepository<VehicleOwner, Long> {
//    public boolean existsVehicleOwnerByNic(String nic);
//    public VehicleOwner findVehicleOwnerByNic(String nic);
    Optional<VehicleOwner> findByEmail(String email);
    boolean existsVehicleOwnerByEmail(String email);
}
