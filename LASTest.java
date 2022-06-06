import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.console.ConsoleLauncher;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LASTest {
    // Data Wrangler Tests
    /**
     * Ken Chen
     * Data Wrangler
     * test if the loadFile() method works as expected when the given file path is wrong or the
     * file doesn't exist.
     */
    @Test
    public void dataWrangler_testFileNotFind() {
        // test if the method will throw an exception if the path is incorrect
        assertThrows(FileNotFoundException.class, () -> {
            List<AirportInterface> airport = new AirportDataLoader().loadFile("./random.csv");
        });
    }

    /**
     * Ken Chen
     * Data Wrangler
     * test if the target data file can be loaded properly.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void dataWrangler_testFileLoad() throws FileNotFoundException {
        List<AirportInterface> airports = new AirportDataLoader().loadFile("./airports.csv");
        assertEquals(true, airports.get(0) instanceof AirportInterface);
    }

    /**
     * Ken Chen
     * Data Wrangler
     * Test if the functionality of airport class works or not.
     */
    @Test
    public void dataWrangler_testAirportMethods() throws FileNotFoundException {
        List<AirportInterface> airports = new AirportDataLoader().loadFile("./airports.csv");
        AirportInterface temp = airports.get(0);

        assertEquals("Alabama", temp.getStateName());
        assertEquals("Montgomery", temp.getAirportName());
        assertEquals(32.377716, temp.getLatitude());
        assertEquals(-86.300568, temp.getLongitude());
    }

    // Back End Developer Tests
    /**
     * Wei-Jen Chen
     * BackendDeveloper
     * This test is to test about whether findAirport method work
     * It should return the airport name when you provide the State Name
     */
    @org.junit.jupiter.api.Test
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
    @org.junit.jupiter.api.Test
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
    @org.junit.jupiter.api.Test
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

    // Front End Developer Tests


    // Integration Manager Tests
    /**
     * start test
     * @param args
     */
    public static void main(String[] args) {
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
     * test the correctness of backend findAirport() method using real data.
     */
    @Test
    public void integration_testFindAirport() {
        List<AirportInterface> airports = null;
        try {
            airports = new AirportDataLoader().loadFile("airports.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LASBackEndInterface engine = new LASBackEnd(796);
        for(AirportInterface airport : airports) engine.addAirport(airport);

        assertEquals("[Madison]", engine.findAirport("Wisconsin").toString());
        assertEquals("[Springfield]", engine.findAirport("Illinois").toString());
        assertEquals("[Lansing]", engine.findAirport("Michigan").toString());
    }

    /**
     * test the correctness of backend calculateDistance() method using real data.
     */
    @Test
    public void integration_testCalculateDistance() {
        List<AirportInterface> airports = null;
        try {
            airports = new AirportDataLoader().loadFile("airports.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LASBackEndInterface engine = new LASBackEnd(796);
        for(AirportInterface airport : airports) engine.addAirport(airport);

        assertEquals(396, engine.calculateDistance("Lansing", "Madison"));
        assertEquals(396, engine.calculateDistance("Madison", "Lansing"));
        assertEquals(537, engine.calculateDistance("Lansing", "Springfield"));
        assertEquals(366, engine.calculateDistance("Madison", "Springfield"));
    }

    /**
     * test whether the backend engine draws the correct graph as it is supposed to do,
     * using real data.
     */
    @Test
    public void integration_testDrawGraph() {
        List<AirportInterface> airports = null;
        try {
            airports = new AirportDataLoader().loadFile("airports.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LASBackEnd engine = new LASBackEnd(796);
        for(AirportInterface airport : airports) engine.addAirport(airport);
        engine.drawGraph();

        for(AirportInterface airport : airports) {
            assertTrue(engine.getGraph().containsVertex(airport.getAirportName()));
        }

        for(AirportInterface airport1 : airports) {
            for(AirportInterface airport2 : airports) {
                if (engine.calculateDistance(airport1.getAirportName(), airport2.getAirportName()) <= 796) {
                    assertEquals(true, engine.getGraph().containsEdge(airport1.getAirportName(), airport2.getAirportName()));
                } else {
                    assertEquals(false, engine.getGraph().containsEdge(airport1.getAirportName(), airport2.getAirportName()));
                }
            }
        }
    }
}
