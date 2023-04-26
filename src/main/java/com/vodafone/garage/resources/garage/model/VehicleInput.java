package com.vodafone.garage.resources.garage.model;

import lombok.Data;
import com.vodafone.garage.model.enums.VehicleEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class VehicleInput {

  @Size(min = 1,max = 30)
  @NotNull
  private String plate;

  @Size(min = 1,max = 30)
  @NotNull
  private String color;

  @NotNull
  private VehicleEnum vehicleType;

}