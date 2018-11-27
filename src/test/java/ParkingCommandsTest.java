import com.polestar.CPMStarter;
import com.polestar.configuration.H2Config;
import com.polestar.model.ParkingCommand;
import com.polestar.model.ParkingPlace;
import com.polestar.model.ParkingSpace;
import com.polestar.repository.ParkingPlaceRepository;
import com.polestar.repository.ParkingRepository;
import com.polestar.service.ParkingService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CPMStarter.class, H2Config.class})
@EnableAutoConfiguration
@ActiveProfiles("test")
public class ParkingCommandsTest {

    @Autowired
    private ParkingService parkingService;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ParkingPlaceRepository parkingPlaceRepository;


    @Test
    public void testParking(){

        List<ParkingCommand> parkingCommands = new ArrayList<>();

        String input = "pABC,pXYZ,pEFG,u5000,c";

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
                case 'c': parkingService.optimizeParkingSpaces();
                    System.out.print(", "+p.getSymbol());break;
                case 'p': parkingService.parkCar(p.getPlate());
                    System.out.print(p.getSymbol()+" "+p.getPlate()+", ");break;
                case 'u': parkingService.unparkCar(Integer.valueOf(p.getPlate()));
                    System.out.print(p.getSymbol()+" "+p.getPlate()+", ");break;
            }
        }
    }
}
