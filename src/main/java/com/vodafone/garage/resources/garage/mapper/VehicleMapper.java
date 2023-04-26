package com.vodafone.garage.resources.garage.mapper;

import com.vodafone.garage.resources.garage.model.VehicleInput;
import com.vodafone.garage.service.garage.model.Vehicle;

public class VehicleMapper {

    public static Vehicle toVehicle(VehicleInput input) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(input.getPlate());
        vehicle.setColor(input.getColor());
        vehicle.setVehicleType(input.getVehicleType());
        return vehicle;
    }

}
