package com.quikdeliver.repository;

import com.quikdeliver.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Modifying
    @Query("UPDATE Vehicle as v SET v = ?1 WHERE v.id = ?2 AND v.vehicleOwner.id = ?3")
    void updateVehicle(Vehicle vehicle, Long id, Long userId);

    @Query("SELECT v FROM Vehicle as v WHERE v.vehicleOwner.id = ?1 AND v.id = ?2")
    Vehicle getVehicle(Long userId, Long id);

//    @Modifying
//    @Query("UPDATE Vehicle as v SET v.isDeleted = true WHERE v.id = ?1 AND v.vehicleOwner.id = ?2")
//    void deleteVehicle(Long id, Long userId);
}