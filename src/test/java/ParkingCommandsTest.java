import com.polestar.CPMStarter;
import com.polestar.configuration.H2Config;
import com.polestar.model.ParkingCommand;
import com.polestar.model.ParkingPlace;
import com.polestar.model.ParkingSpace;
import com.polestar.repository.ParkingPlaceRepository;
import com.polestar.repository.ParkingRepository;
import com.polestar.service.ParkingService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
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
                    break;
                case 'p':
                    Assert.assertTrue("car with plate:"+p.getPlate()+" was parked",parkingService.parkCar(p.getPlate()));
                    break;
                case 'u':
                    Assert.assertTrue("car with plate:"+p.getPlate()+" was unpark",
                            parkingService.unparkCar(Integer.valueOf(p.getPlate())));
                    break;
            }
        }
    }
}
