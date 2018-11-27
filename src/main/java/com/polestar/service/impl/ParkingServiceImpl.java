package com.polestar.service.impl;

import com.polestar.model.ParkingCommand;
import com.polestar.model.ParkingPlace;
import com.polestar.model.ParkingSpace;
import com.polestar.model.Ticket;
import com.polestar.repository.ParkingPlaceRepository;
import com.polestar.repository.ParkingRepository;
import com.polestar.service.ParkingService;
import com.polestar.service.TicketService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@NoArgsConstructor
public class ParkingServiceImpl implements ParkingService {

    private final Integer PARKING_ID = 1;
    private final  String PARKING_NAME = "My Parking";

    private ParkingRepository parkingRepository;
    private TicketService ticketService;
    private ParkingPlaceRepository placeRepository;

    @Autowired
    public ParkingServiceImpl(ParkingRepository repository, TicketService ticketService, ParkingPlaceRepository placeRepository) {
        this.parkingRepository = repository;
        this.ticketService = ticketService;
        this.placeRepository = placeRepository;
    }


    @PostConstruct
    public void init(){

        ParkingSpace space = new ParkingSpace();
        List<ParkingPlace> places = new ArrayList<>();

        for (int i = 1; i <= 10 ; i++) {
            ParkingPlace place = new ParkingPlace();
            place.setBusy(Boolean.FALSE);
            place.setNearToEntrance(i);
            places.add(placeRepository.save(place));
        }
        space.setParkingPlaces(places);
        space.setId(PARKING_ID);
        space.setParkingName(PARKING_NAME);

        ParkingSpace createdSpace = parkingRepository.save(space);
        log.debug("create new space with id:{} name:{}",createdSpace.getId(),createdSpace.getParkingName());


        List<ParkingCommand> parkingCommands = new ArrayList<>();

        String input = "pABC,pXYZ,pEFG,u5000,u5001,c";

        String[] sArr = input.split(",");
        ParkingCommand parkingCommand = null;
        for(String string : sArr ){
            char command = string.charAt(0);
            parkingCommand = new ParkingCommand();
            parkingCommand.setSymbol(command);
            if(command != 'c'){
                parkingCommand.setPlate( string.substring(1));

            }
            parkingCommands.add(parkingCommand);
        }

        for(ParkingCommand p : parkingCommands){
            switch (p.getSymbol()){
                case 'c': optimizeParkingSpaces();
                    System.out.println(p.getSymbol());break;
                case 'p': parkCar(p.getPlate());
                    System.out.println(p.getSymbol()+p.getPlate());break;
                case 'u': unparkCar(Integer.valueOf(p.getPlate()));
                    System.out.println(p.getSymbol()+p.getPlate());break;
            }
        }

    }

    @Override
    public boolean checkForFreeSpace(Integer parkingId) {
        Set<Boolean> booleanSet = new HashSet<>();
        ParkingSpace space = parkingRepository.findOne(parkingId);
        space.getParkingPlaces().forEach(p ->{
            if(p.isBusy()){
                booleanSet.add(Boolean.TRUE);
            }else {
                booleanSet.add(Boolean.FALSE);
            }
        });

        boolean result = booleanSet.contains(Boolean.FALSE);
        log.debug("parkignId:  is parking have free space: {}",space.getId(), result);

        return result;
    }

    @Override
    public ParkingPlace parkCar(String plate) {
        ParkingSpace space = parkingRepository.findOne(PARKING_ID);

        if(space != null){
            boolean check = checkForFreeSpace(space.getId());
            if(check){
                ParkingPlace parkingPlace = findFreeNearToEntranceSpace(space.getParkingPlaces());
                parkingPlace.setCarPlate(plate);
                parkingPlace.setBusy(Boolean.TRUE);

                Ticket ticket = ticketService.incrementTicketNumber();
                parkingPlace.setTicketNum(ticket.getTicketNum());
                ticketService.updateTicketNumber(ticket);

                placeRepository.save(parkingPlace);
                parkingRepository.save(space);

                return parkingPlace;
            }
        }

        return null;
    }

    @Override
    public void optimizeParkingSpaces() {
        List<ParkingPlace> places = new ArrayList<>();
        ParkingSpace parkingSpace = parkingRepository.findOne(PARKING_ID);
        AtomicInteger atomicInteger = new AtomicInteger(1);
        parkingSpace.getParkingPlaces().forEach(p ->{
            if(p.isBusy()){
                ParkingPlace parkingPlace = new ParkingPlace();
                BeanUtils.copyProperties(p,parkingPlace);
                parkingPlace.setNearToEntrance(atomicInteger.get());
                atomicInteger.set(atomicInteger.get()+1);
                places.add(parkingPlace);
            }else if(!p.isBusy()){
                ParkingPlace parkingPlace = new ParkingPlace();
                parkingPlace.setNearToEntrance(atomicInteger.get());
                atomicInteger.set(atomicInteger.get()+1);
                places.add(parkingPlace);
            }
        });

        parkingSpace.setParkingPlaces(places);
        parkingRepository.save(parkingSpace);

    }

    @Override
    public boolean unparkCar(Integer ticketNum) {

        List<ParkingPlace> places = placeRepository.findAll();

        ParkingPlace place = null;
        for (int i = 0; i < places.size() ; i++) {
            place  = places.get(i);
            if(place.getTicketNum().intValue() == ticketNum.intValue()){
                ParkingSpace space = parkingRepository.findOne(PARKING_ID);
                space.getParkingPlaces().remove(place);
                parkingRepository.save(space);

                placeRepository.delete(place);
                return true;
            }
        }

        return false;
    }


    private ParkingPlace findFreeNearToEntranceSpace(List<ParkingPlace> spaces){

        ParkingPlace place = null;
        for (int i = 0; i < spaces.size(); i++) {
            if(!spaces.get(i).isBusy()){
                place = spaces.get(i);
                break;
            }
        }
        return place;
    }
}
