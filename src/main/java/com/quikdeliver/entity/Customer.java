package com.quikdeliver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quikdeliver.model.AuthProvider;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends User {

    public Customer(String email, String password, String name, Collection<Role> roles, AuthProvider provider){
        super(email,password,name,roles,provider);
    }
    @JsonIgnore
    @OneToMany(mappedBy = "customer", orphanRemoval = true, cascade=CascadeType.ALL)
    private Set<PackageDeliveryRequest> deliverBookings = new HashSet<>();

    public void addDeliverBooking(PackageDeliveryRequest deliverBooking) {
        deliverBooking.setCustomer(this);
    }
}
