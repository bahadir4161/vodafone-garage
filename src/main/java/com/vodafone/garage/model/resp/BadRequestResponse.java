package com.vodafone.garage.model.resp;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestResponse<T> extends GarageResponse<T> {

  public BadRequestResponse(String message) {
    this(Arrays.asList(new ResponseError(message)));
  }

  public BadRequestResponse(List<ResponseError> errors) {
    super(null, errors, BAD_REQUEST);
  }
}
