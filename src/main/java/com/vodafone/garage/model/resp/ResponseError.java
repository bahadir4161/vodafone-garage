package com.vodafone.garage.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
  private String message;
  private String field;

  public ResponseError(String message) {
    this.message = message;
    this.field = null;
  }
}
