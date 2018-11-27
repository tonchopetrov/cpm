package com.polestar.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "parking_space")
public class ParkingSpace {

    @Id
    private Integer id = 1;
    @Column
    private String parkingName;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ParkingPlace> parkingPlaces;
}


