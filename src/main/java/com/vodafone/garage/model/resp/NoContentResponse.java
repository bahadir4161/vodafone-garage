package com.vodafone.garage.model.resp;

import lombok.Getter;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Getter
public class NoContentResponse extends GarageResponse {
  public NoContentResponse() {
    super(null, null, NO_CONTENT);
  }
}
