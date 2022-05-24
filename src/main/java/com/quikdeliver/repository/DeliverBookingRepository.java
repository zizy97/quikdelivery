package com.quikdeliver.repository;

import com.quikdeliver.entity.PackageDeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverBookingRepository extends JpaRepository<PackageDeliveryRequest, Long> {
}
