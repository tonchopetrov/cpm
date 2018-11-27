package com.polestar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer ticketNum;
    private String carPlate;
    private boolean isBusy;
    // scale 1-10 how close to entrance is this place
    private Integer nearToEntrance = 1;
}
