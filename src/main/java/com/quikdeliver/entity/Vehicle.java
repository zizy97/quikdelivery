package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.*;
import com.quikdeliver.model.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "vehicles")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "type")
    private VehicleType type;

    @Column(name = "current_allocation_status")
    private boolean currentAllocationStatus;

    @Column(name = "description")
    private String vehicleDescription;

    @ManyToOne
    @JoinColumn(name="vehicle_owner_id")
    private VehicleOwner vehicleOwner;

    private boolean isDeleted;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Allocation> allocations;
}
