package com.quikdeliver.service;

import com.quikdeliver.entity.PackageDeliveryRequest;
import com.quikdeliver.repository.DeliverBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverBookingService {
    private final DeliverBookingRepository deliverBookingRepository;
    @Autowired
    public DeliverBookingService(DeliverBookingRepository deliverBookingRepository) {
        this.deliverBookingRepository = deliverBookingRepository;
    }

    public void addDeliverBooking(PackageDeliveryRequest deliverBooking) {
        deliverBookingRepository.save(deliverBooking);
    }

    public PackageDeliveryRequest getDeliverBooking(Long id) {
        return deliverBookingRepository.findById(id).get();
    }

    public PackageDeliveryRequest updateDeliverBooking(PackageDeliveryRequest deliverBooking) {
        return deliverBookingRepository.save(deliverBooking);
    }
}
