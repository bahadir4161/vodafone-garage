package com.vodafone.garage.model.resp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class GarageResponse<T> extends ResponseEntity<ResponseBody> {
  public GarageResponse(T data, List<ResponseError> errors, HttpStatus status) {
    super(new ResponseBody(data, errors), status);
  }
}
