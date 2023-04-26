package com.vodafone.garage.model.enums;

public enum VehicleEnum {
    Car("Car", 1), Jeep("Jeep", 2), Truck("Truck", 4);

    private final String key;
    private final Integer value;

    VehicleEnum(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}
