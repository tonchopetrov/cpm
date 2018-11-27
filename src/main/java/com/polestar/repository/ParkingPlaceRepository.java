package com.polestar.repository;

import com.polestar.model.ParkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace,Integer> {
}
