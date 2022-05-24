package com.quikdeliver.model;


public enum VehicleType {
    TWO_TON_VAN("TWO_TON_VAN"), FOUR_TON_TRUCK("FOUR_TON_TRUCK"),
    EIGHT_TON_TRUCK("EIGHT_TON_TRUCK"), TEN_TON_TRUCK("TEN_TON_TRUCK");
    private final String value;

    VehicleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
