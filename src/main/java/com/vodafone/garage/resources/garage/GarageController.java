package com.vodafone.garage.resources.garage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.vodafone.garage.model.resp.GarageResponse;
import com.vodafone.garage.resources.garage.model.VehicleInput;
import com.vodafone.garage.service.garage.GarageService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/garage", produces = "application/json")
public class GarageController {

  private final GarageService garageService;

  @PostMapping("/park")
  public GarageResponse<String> park(@Valid @RequestBody VehicleInput input) {
    log.debug("/park controller called.");
    log.info("input received : {}" , input);
    return garageService.park(input);
  }

  @PutMapping("/leave/{ticketNumber}")
  public GarageResponse<String> leave(@Valid  @PathVariable("ticketNumber") @Positive Integer ticketNumber) {
    log.debug("/leave controller called.");
    log.info("input received : {}" , ticketNumber);
    return garageService.leave(ticketNumber);
  }

  @GetMapping("/status")
  public GarageResponse<String> status() {
    log.debug("/status controller called.");
    return garageService.status();
  }


  @GetMapping("/clear-cache")
  public void clearCache() {
    log.debug("/clear-cache controller called.");
    garageService.clearCache();
  }







}
