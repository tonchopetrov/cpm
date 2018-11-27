package com.polestar;

import com.polestar.model.ParkingCommand;
import com.polestar.service.impl.ParkingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootApplication
public class CPMStarter {


    private static Map<String,String> commands = new LinkedHashMap<>();
    public static void main(String[] args) {
        log.info("Service is  Starting !!!");
        SpringApplication.run(CPMStarter.class, args);
        log.info("Service Started !!!");



//        List<ParkingCommand> parkingCommands = new ArrayList<>();
//
//        String[] sArr = readInput().split(",");
//        ParkingCommand parkingCommand = null;
//        for(String string : sArr ){
//            char command = string.charAt(0);
//            parkingCommand = new ParkingCommand();
//            parkingCommand.setSymbol(command);
//            if(command != 'c'){
//               parkingCommand.setPlate( string.substring(1));
//
//            }
//            parkingCommands.add(parkingCommand);
//        }
//
//        ParkingServiceImpl parkingService = new ParkingServiceImpl();
//
//        for(ParkingCommand p : parkingCommands){
//            switch (p.getSymbol()){
//                case 'c': parkingService.optimizeParkingSpaces();
//                    System.out.print(", "+p.getSymbol());break;
//                case 'p': parkingService.parkCar(p.getPlate());
//                    System.out.print(p.getSymbol()+" "+p.getPlate()+", ");break;
//                case 'u': parkingService.unparkCar(Integer.valueOf(p.getPlate()));
//                    System.out.print(p.getSymbol()+" "+p.getPlate()+", ");break;
//            }
//        }
    }


    private static String readInput(){
        System.out.print("Enter command and plate: ");
        Scanner sc = new Scanner(System.in);
        String s = null;
        if(sc.hasNext()){
            s = sc.next();
        }
        return s;
    }

    @PreDestroy
    private void stop(){
        log.info("Service stopped !");
    }
}
