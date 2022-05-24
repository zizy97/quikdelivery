package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table(name = "vehicle_owners")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class VehicleOwner extends User {

    @OneToMany(mappedBy="vehicleOwner",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles=new HashSet<>();

    @OneToMany(mappedBy="vehicleOwner",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Driver> driver=new HashSet<>();

    @Column(columnDefinition = "boolean default false")
    private boolean isVerified;

    public void addVehicle(Vehicle vehicle){
        vehicle.setVehicleOwner(this);
    }
    public void addDriver(Driver driver){
        driver.setVehicleOwner(this);
    }
}
