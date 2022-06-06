import java.io.FileNotFoundException;
import java.util.List;

public class LASApp {
    public static void main(String[] args) {
        LASFrontEndInterface ui = new LASFrontEnd();
        List<AirportInterface> airports = null;
        try {
            airports = new AirportDataLoader().loadFile("airports.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LASBackEndInterface engine = new LASBackEnd(796);
        for(AirportInterface airport : airports) engine.addAirport(airport);
        ui.run(engine, args);
    }
}
