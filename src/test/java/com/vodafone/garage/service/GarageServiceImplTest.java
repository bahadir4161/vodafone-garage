package com.vodafone.garage.service;

import com.vodafone.garage.model.enums.VehicleEnum;
import com.vodafone.garage.model.resp.GarageResponse;
import com.vodafone.garage.resources.garage.model.VehicleInput;
import com.vodafone.garage.service.garage.GarageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GarageServiceImplTest {

    @Autowired
    GarageService garageService;

    @Test
    @Order(1)
    void park_1(){
        VehicleInput input=new VehicleInput();
        input.setPlate("34-SO-1988");
        input.setColor("Black");
        input.setVehicleType(VehicleEnum.Car);
        GarageResponse<String> response = garageService.park(input);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("TicketNumber : 1 - Allocated 1 slot.",response.getBody().getData());
    }

    @Test
    @Order(2)
    void park_2(){
        VehicleInput input=new VehicleInput();
        input.setPlate("34-BO-1987");
        input.setColor("Red");
        input.setVehicleType(VehicleEnum.Truck);
        GarageResponse<String> response = garageService.park(input);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("TicketNumber : 2 - Allocated 4 slots.",response.getBody().getData());
    }

    @Test
    @Order(3)
    void park_3(){
        VehicleInput input=new VehicleInput();
        input.setPlate("34-VO-2018");
        input.setColor("Blue");
        input.setVehicleType(VehicleEnum.Jeep);
        GarageResponse<String> response = garageService.park(input);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("TicketNumber : 3 - Allocated 2 slots.",response.getBody().getData());
    }

    @Test
    @Order(4)
    void park_4(){
        VehicleInput input=new VehicleInput();
        input.setPlate("34-HBO-2020");
        input.setColor("Black");
        input.setVehicleType(VehicleEnum.Truck);
        GarageResponse<String> response = garageService.park(input);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("Garage is full.",response.getBody().getData());
    }

    @Test
    @Order(5)
    void leave_1(){
        GarageResponse<String> response = garageService.leave(3);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("OK",response.getBody().getData());
    }

    @Test
    @Order(6)
    void park_5(){
        VehicleInput input=new VehicleInput();
        input.setPlate("34-LO-2000");
        input.setColor("White");
        input.setVehicleType(VehicleEnum.Car);
        GarageResponse<String> response = garageService.park(input);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals("TicketNumber : 4 - Allocated 1 slot.",response.getBody().getData());
    }

    @Test
    @Order(7)
    void status_1(){
        GarageResponse<String> response = garageService.status();
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        List<String> data=new ArrayList<>();
        data.add("TicketNumber : 1 - 34-SO-1988 Black [1]");
        data.add("TicketNumber : 2 - 34-BO-1987 Red [3,4,5,6]");
        data.add("TicketNumber : 4 - 34-LO-2000 White [8]");
        List<String> responseData = (List<String>) response.getBody().getData();
        Assertions.assertArrayEquals(data.toArray(),responseData.toArray());
    }

    @Test
    @Order(8)
    void clearCache(){
        GarageResponse<String> response = garageService.clearCache();
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @Order(9)
    void status_2(){
        GarageResponse<String> response = garageService.status();
        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

}