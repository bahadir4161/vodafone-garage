package com.vodafone.garage.model.resp;

import static org.springframework.http.HttpStatus.OK;

public class OkResponse<T> extends GarageResponse<T> {
  public OkResponse(T data) {
    super(data, null, OK);
  }
}
