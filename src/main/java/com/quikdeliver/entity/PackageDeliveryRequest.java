package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.quikdeliver.model.PackageDeliveryRequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "deliver_booking")
public class PackageDeliveryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "dropoff_address")
    private String dropOffAddress;

    @Column(name = "special_remarks")
    private String specialRemarks;

    @Column(name = "package_description")
    private String packageDescription;

    @Column(name = "weight")
    private double weight;

    @Column(name = "pickup_date")
    private LocalDate pickupDate;

    @Column(name = "pickup_time")
    private String pickupTime;

    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "distance")
    private double distance;

    @Column(name = "allocated")
    private boolean allocated;

    @OneToOne(mappedBy = "packageDeliveryRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Allocation allocation;

    @Column(name = "status")
    private PackageDeliveryRequestStatus status;
}
