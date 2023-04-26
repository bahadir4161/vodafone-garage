package com.vodafone.garage.resources;

import lombok.extern.slf4j.Slf4j;
import com.vodafone.garage.model.resp.BadRequestResponse;
import com.vodafone.garage.model.resp.GarageResponse;
import com.vodafone.garage.model.resp.ResponseError;
import com.vodafone.garage.model.resp.ServerErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public GarageResponse handleServerException(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return new ServerErrorResponse(ex.getMessage());
  }

  @ExceptionHandler(NullPointerException.class)
  public GarageResponse handleNullPointerException(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return new ServerErrorResponse(ExceptionUtils.getStackTrace(ex));
  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    log.error(ex.getMessage(), ex);
    List<ResponseError> errors = ex.getBindingResult()
        .getFieldErrors().stream()
        .map(e -> new ResponseError(e.getDefaultMessage(), e.getField()))
        .collect(Collectors.toList());

    return new BadRequestResponse(errors);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return new BadRequestResponse(ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex) {
    log.error(ex.getMessage(), ex);
    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
    List<ResponseError> errors = constraintViolations.stream()
        .map(constraintViolation -> new ResponseError(constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()))
        .collect(Collectors.toList());
    return new BadRequestResponse(errors);
  }

}
