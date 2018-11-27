package com.polestar.service;

import com.polestar.model.ParkingPlace;
import com.polestar.model.ParkingSpace;

import java.util.List;

public interface ParkingService {

    boolean checkForFreeSpace(Integer parkingId);
    ParkingPlace parkCar(String plate);
    boolean unparkCar(Integer ticketNum);
    void optimizeParkingSpaces();
}
