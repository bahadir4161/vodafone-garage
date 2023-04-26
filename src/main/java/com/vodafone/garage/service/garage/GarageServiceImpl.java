package com.vodafone.garage.service.garage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.vodafone.garage.model.resp.GarageResponse;
import com.vodafone.garage.model.resp.NoContentResponse;
import com.vodafone.garage.model.resp.OkResponse;
import com.vodafone.garage.resources.garage.model.VehicleInput;
import com.vodafone.garage.resources.garage.mapper.VehicleMapper;
import com.vodafone.garage.service.garage.model.Slot;
import com.vodafone.garage.service.garage.model.Ticket;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements GarageService {
    public static List<Slot> slots = null;
    public static Integer ticketNumber = 0;

    @PostConstruct
    public void initializeSlots() {
        log.debug("initializeSlots method called.");
        if (slots == null) {
            slots = new ArrayList<>();
            for (int i = 1; i < 11; i++) {
                Slot slot = new Slot();
                slot.setSlotNumber(i);
                slot.setTicket(null);
                slots.add(slot);
            }
            log.info("Garage Slots Ready. Slot Count : {}", slots.size());
        }
    }

    @Override
    public GarageResponse<String> park(VehicleInput input) {
        log.debug("park service called.");
        List<Slot> emptySlots = slots.stream().filter(slot -> slot.getTicket() == null).collect(Collectors.toList());
        if (emptySlots.size() < input.getVehicleType().getValue()) {
            return new OkResponse("Garage is full.");
        }
        if (emptySlots.size() == 10) {
            Integer allocatedSlotCount = 0;
            Ticket newTicket = createNewTicket(input);
            for (int i = 0; i < input.getVehicleType().getValue(); i++) {
                slots.get(i).setTicket(newTicket);
                allocatedSlotCount++;
            }
            return new OkResponse("TicketNumber : "+newTicket.getTicketNumber()+" - Allocated " + allocatedSlotCount + (allocatedSlotCount == 1 ? " slot." : " slots."));
        } else {
            return new OkResponse("Garage is full.");
        }
    }

    @Override
    public GarageResponse<String> leave(Integer ticketNumber) {
        log.debug("leave service called.");
        for (Slot slot : slots) {
            if (slot.getTicket() != null && slot.getTicket().getTicketNumber().equals(ticketNumber)) {
                slot.setTicket(null);
            }
        }
        return new OkResponse("OK");
    }

    @Override
    public GarageResponse<String> status() {
        log.debug("status service called.");
        List<Slot> parkedVehicles = slots.stream().filter(slot -> slot.getTicket() != null).collect(Collectors.toList());
        List<String> parkedVehicleInfo=new ArrayList<>();
        for (Slot parkedVehicle : parkedVehicles) {
            List<Slot> parkedVehicleSlots = parkedVehicles.stream().filter(slot -> slot.getTicket().getTicketNumber().equals(parkedVehicle.getTicket().getTicketNumber())).collect(Collectors.toList());
            StringBuilder vehicleSlotString= new StringBuilder("[");
            for (Slot parkedVehicleSlot : parkedVehicleSlots) {
                vehicleSlotString.append(parkedVehicleSlot.getSlotNumber().toString()).append(",");
            }
            vehicleSlotString = new StringBuilder(vehicleSlotString.substring(0, vehicleSlotString.length() - 1) + "]");
            vehicleSlotString=new StringBuilder("TicketNumber : " + parkedVehicle.getTicket().getTicketNumber()+" - "+parkedVehicle.getTicket().getVehicle().getPlate()+" "+ parkedVehicle.getTicket().getVehicle().getColor() +" "+ vehicleSlotString);
            if (!parkedVehicleInfo.contains(vehicleSlotString.toString())){
                parkedVehicleInfo.add(vehicleSlotString.toString());
            }

        }
        if (parkedVehicleInfo.size()>0){
            return new OkResponse(parkedVehicleInfo);
        }else {
            return new NoContentResponse();
        }

    }

    @Override
    public GarageResponse<String> clearCache() {
        log.debug("clearCache service called.");
        ticketNumber=0;
        for (Slot slot : slots) {
            slot.setTicket(null);
        }
        return new OkResponse("OK");
    }
    //park işlemi
    private String parkVehicleToGarage(VehicleInput input, List<Slot> availableSlotList){
        log.debug("parkVehicleToGarage method called.");
        Integer requiredSlotCount = input.getVehicleType().getValue();
        Ticket newTicket = createNewTicket(input);
        Integer targetSlotNumber=0;
        if (availableSlotList.size()==1){
            targetSlotNumber = availableSlotList.get(0).getSlotNumber();
        }else {
            targetSlotNumber = getTargetSlot(availableSlotList).getSlotNumber();
        }
        requiredSlotCount--;
        Integer allocatedSlotCount = 0;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getSlotNumber().equals(targetSlotNumber) && slots.get(i).getTicket() == null) {
                slots.get(i).setTicket(newTicket);
                allocatedSlotCount++;
                if (requiredSlotCount != 0) {
                    targetSlotNumber++;
                    requiredSlotCount--;
                }
            }
        }
        return "TicketNumber : "+newTicket.getTicketNumber()+" - Allocated " + allocatedSlotCount + (allocatedSlotCount == 1 ? " slot." : " slots.");
    }
    
    private Slot getTargetSlot(List<Slot> availableSlots) {
        log.debug("getTargetSlot method called.");
        for (Slot availableSlot : availableSlots) {
            List<Slot> slots = availableSlots.stream().filter(slot -> slot.getSlotNumber().equals(availableSlot.getSlotNumber() + 1)).collect(Collectors.toList());
            if (slots.size()>0){
                return slots.get(0);
            }
        }
        return availableSlots.get(0);
    }



    private Ticket createNewTicket(VehicleInput input) {
        log.debug("createNewTicket method called.");
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(getNewGarageTicketNumber());
        ticket.setVehicle(VehicleMapper.toVehicle(input));
        return ticket;
    }

    private Integer getNewGarageTicketNumber() {
        log.debug("getNewTicketNumber method called.");
        return ticketNumber += 1;
    }
}
