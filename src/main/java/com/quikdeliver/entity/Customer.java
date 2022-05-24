package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade=CascadeType.ALL)
    private Set<PackageDeliveryRequest> deliverBookings = new HashSet<>();

    public void addDeliverBooking(PackageDeliveryRequest deliverBooking) {
        deliverBooking.setCustomer(this);
    }
}
