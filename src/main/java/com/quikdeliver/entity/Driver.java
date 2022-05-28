package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "drivers")
public class Driver extends User {
    @Column(name = "license")
    private String license;
    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;
    @Column(name = "other_details")
    private String otherDetails;
    @Column(name = "hourly_wage")
    private double hourlyWage;
    @Column(name = "current_allocation_status")
    private boolean currentAllocationStatus;
    @Column(name="verified",columnDefinition = "boolean default false")
    private boolean isVerified;

    @ManyToOne
    @JoinColumn(name="vehicle_owner_id")
    private VehicleOwner vehicleOwner;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Allocation> allocations;

    public Driver(String email, String password, String name, Collection<Role> roles){
        super(email,password,name,roles);
    }
}
