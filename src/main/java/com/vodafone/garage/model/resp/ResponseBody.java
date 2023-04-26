package com.vodafone.garage.model.resp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseBody<T> {
  private T data;
  private List<ResponseError> errors;
  private Long timestamp;

  public ResponseBody(T data, List<ResponseError> errors) {
    this.data = data;
    this.errors = errors != null ? errors : new ArrayList<>();
    this.timestamp = System.currentTimeMillis();
  }
}
