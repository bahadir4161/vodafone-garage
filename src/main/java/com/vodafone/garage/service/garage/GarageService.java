package com.vodafone.garage.service.garage;

import com.vodafone.garage.model.resp.GarageResponse;
import com.vodafone.garage.resources.garage.model.VehicleInput;

public interface GarageService {

    GarageResponse<String> park(VehicleInput input);
    GarageResponse<String> leave(Integer ticketNumber);
    GarageResponse<String> status();
    GarageResponse<String> clearCache();
}
