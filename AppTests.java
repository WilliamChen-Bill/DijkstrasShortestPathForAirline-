
import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandles;


public class AppTests {
    
    public static void main (String[] args) throws Exception{
        String className = MethodHandles.lookup().lookupClass().getName();
        String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
        String[] arguments = new String[] {
          "-cp",
          classPath,
          "--include-classname=.*",
          "--select-class=" + className };
        ConsoleLauncher.main(arguments);
        
    }
    
    /**
     * Wei-Jen Chen
     * BackendDeveloper
     * This test is to test about whether findAirport method work
     * It should return the airport name when you provide the State Name
     */
    @Test
    public void backendDeveloper_TestFindAirport(){
        LASBackEnd engine = new LASBackEnd(796);
        engine.addAirport(new AirportPlaceholderA());
        engine.addAirport(new AirportPlaceholderB());
        engine.addAirport(new AirportPlaceholderC());
        assertTrue(engine.findAirport("Wisconsin").toString().equals("[Madison]"));
    }

    /**
     * Wei-Jen Chen
     * BackendDeveloper
     * This method show calculate the distance between two place with 
     * Haversine formula and ceiling the result to the nearest integer value.
     * Fligth distance between Lansing and Madison should be 396km
     */
    @Test
    public void backendDeveloper_TestCalculateDistance(){
        LASBackEnd engine = new LASBackEnd(796);
        engine.addAirport(new AirportPlaceholderA());
        engine.addAirport(new AirportPlaceholderB());
        engine.addAirport(new AirportPlaceholderC());
        assertTrue(engine.calculateDistance("Lansing", "Madison") == 396);
    }

    /**
     * Wei-Jen Chen
     * BackendDeveloper
     * This method should test about whether we have draw the graph or not.
     * In this case the maximum flight capacity of the airplane is 796
     * which means between Madison, Springfield, and Lansing airport should exist 
     * an edge connected each other.
     */
    @Test
    public void backendDeveloper_TestDrawGraph(){
        LASBackEnd engine = new LASBackEnd(796);
        engine.addAirport(new AirportPlaceholderA());
        engine.addAirport(new AirportPlaceholderB());
        engine.addAirport(new AirportPlaceholderC());
        engine.drawGraph();
        assertTrue(engine.getGraph().containsVertex("Madison"));
        assertTrue(engine.getGraph().containsVertex("Springfield"));
        assertTrue(engine.getGraph().containsVertex("Lansing"));
        assertTrue(engine.getGraph().containsEdge("Madison", "Springfield"));
        assertTrue(engine.getGraph().containsEdge("Madison", "Lansing"));
        assertTrue(engine.getGraph().containsEdge("Lansing", "Springfield"));
        assertTrue(engine.getGraph().containsEdge("Springfield", "Lansing"));
        assertTrue(engine.getGraph().containsEdge("Lansing", "Madison"));
        assertTrue(engine.getGraph().containsEdge("Springfield", "Madison"));
    }

}
