package com.polestar.service;

import com.polestar.model.ParkingPlace;


public interface ParkingService {

    boolean checkForFreeSpace(Integer parkingId);
    boolean parkCar(String plate);
    boolean unparkCar(Integer ticketNum);
    void optimizeParkingSpaces();
}
