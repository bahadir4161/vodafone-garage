package com.vodafone.garage.model.resp;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ServerErrorResponse<T> extends GarageResponse<T> {

  public ServerErrorResponse(String message) {
    this(Arrays.asList(new ResponseError(message)));
  }

  public ServerErrorResponse(List<ResponseError> errors) {
    super(null, errors, INTERNAL_SERVER_ERROR);
  }
}
