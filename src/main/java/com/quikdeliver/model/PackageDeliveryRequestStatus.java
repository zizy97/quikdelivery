package com.quikdeliver.model;
//VO -Vehicle Owner
public enum PackageDeliveryRequestStatus {

    DRAFT_REQUEST("PAYMENT_PENDING"),  // 1
    VO_APPROVAL_PENDING("VO_APPROVAL_PENDING"), // 2
    VO_APPROVED("VO_APPROVED"), // 3
    VO_REJECTED("VO_REJECTED"), // 4

    // status for allocated packages
    DELIVERY_IN_PROGRESS("DELIVERY_IN_PROGRESS"), // 5
    DELIVERY_COMPLETED("DELIVERY_COMPLETED");// 6

    private final String value;

    PackageDeliveryRequestStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
