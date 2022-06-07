package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.quikdeliver.model.PackageDeliveryRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "allocations")
public class Allocation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne()
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(columnDefinition = "boolean default false")
    private boolean isAccepted;

    private PackageDeliveryRequestStatus status;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "delivery_duration")
    private double deliveryDuration = 0.0;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "package_delivery_id", nullable = false)
    @JsonManagedReference
    private PackageDeliveryRequest packageDeliveryRequest;

}
