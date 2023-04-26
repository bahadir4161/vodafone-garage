package com.vodafone.garage.service.garage.model;

import lombok.Data;
import com.vodafone.garage.model.enums.VehicleEnum;

@Data
public class Vehicle {
    private String plate;
    private String color;
    private VehicleEnum vehicleType;
}
