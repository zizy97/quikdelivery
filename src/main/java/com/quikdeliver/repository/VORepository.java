package com.quikdeliver.repository;

import com.quikdeliver.entity.Driver;
import com.quikdeliver.entity.User;
import com.quikdeliver.entity.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface VORepository extends JpaRepository<VehicleOwner, Long> {
    Optional<VehicleOwner> findByEmail(String email);
    boolean existsVehicleOwnerByEmail(String email);

    @Modifying
    @Query(value = "insert into vehicle_owners (id) VALUES (:id)", nativeQuery = true)
    @Transactional
    void updateNewRecord( @Param("id") Long id);
}
