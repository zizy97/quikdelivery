package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.*;
import com.quikdeliver.model.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
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

    public VehicleOwner(String email, String password, String name, Collection<Role> roles, AuthProvider provider){
        super(email,password,name,roles,provider);
    }

    public void addVehicle(Vehicle vehicle){
        vehicle.setVehicleOwner(this);
    }
    public void addDriver(Driver driver){
        driver.setVehicleOwner(this);
    }
}
