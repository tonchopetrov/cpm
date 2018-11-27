package com.polestar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParkingCommand {

    private char symbol;
    private String plate;
}
