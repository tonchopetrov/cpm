package com.polestar.repository;


import com.polestar.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSpace,Integer> {

}
