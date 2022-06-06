import java.util.LinkedList;
import java.util.List;

interface LASBackEndInterface{
    /**
     * This method will calculate the shortest flight total flight distance 
     * between two places.
     */
    public int calculateTripDistance(String airportA, String airportB);
    /** 
     * This method will calculate the Haversine formula.
     * The result will be the distance of the two places.
     * The value will be ceiling to the integer.
    */
    public int calculateDistance(String airportA, String airportB);
    /**
     * This method will determine the distance bewteen two airport.
     * If the distance between below than the airplane flight limit,
     * there will be edge exits between two place. 
     * Which means direct path to flight throught.
     */
    public boolean edgeExits(String airportA, String airportB);
    /**
     * This method will find the shortest path from the graph 
     * between two airports.
     * It will show the result as the List<String>
     */
    public List<String> bestRoute(String airportA, String airportB);
    /**
     * This method will find all the airport within a state.
     * Input the state name and it will return all the airports 
     * as List<String>.
     */
    public List<String> findAirport(String state);
    /**
     * This method check whether airport exist or not
     */
    public boolean containAirport(AirportInterface airportInterface);
    /**
     * This will add all the airport data from csv 
     */
    public void addAirport(AirportInterface airport);
    /** 
     * This calculate the times the shortest path need to 
     * transfer to arrive the destination.
     * 0 means no need to transfer direct flight 
    */
    public int calculateStopNumber(String airportA, String airportB);
    /**
     * This will create the Graph base on the desired flight capacity of 
     * the plane.
     * The vertices will be the name of the airport
     * The edges will be the direct path to that airport
     */
    public void drawGraph();
}

public class LASBackEnd implements LASBackEndInterface{
    private int airplantLimitDistance;
    private AirportInterface airportInterface;
    private List<AirportInterface> airports = new LinkedList<>();
    private CS400Graph<String> graph;

    // here to put the desired distance for the plane can flight 
    // dont make it too small otherwise there won't have enough edge exist in the graph
    // reference from online the average trip of the flight is about 796 km
    public LASBackEnd(int airplantLimitDistance){
        this.airplantLimitDistance = airplantLimitDistance;
    }

    @Override
    public int calculateDistance(String airportA, String airportB) {
        // TODO Auto-generated method stub
        /*
        Haversine formula: 
	    ⁃	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
	    ⁃	c = 2 ⋅ atan2( √a, √(1−a) )
	    ⁃	d = R ⋅ c
		where φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
		note that angles need to be in radians to pass to trig functions!
        */
        int R = 6371;
        double latitudeA = 0;
        double latitudeB = 0;
        double longitudeA = 0;
        double longitudeB = 0;
        
        for(int i = 0; i < airports.size(); i++){
            if(airports.get(i).getAirportName().equals(airportA)){
                latitudeA += Math.toRadians(airports.get(i).getLatitude());
                longitudeA += Math.toRadians(airports.get(i).getLongitude());
                
            }
        }
        
        for(int i = 0; i < airports.size(); i++){
            if(airports.get(i).getAirportName().equals(airportB)){
                latitudeB += Math.toRadians(airports.get(i).getLatitude());
                longitudeB += Math.toRadians(airports.get(i).getLongitude());
            }
            
        }
        double a = (Math.pow(Math.sin((latitudeA-latitudeB)/2),2)) + (Math.cos(latitudeA) * Math.cos(latitudeB) * Math.pow(Math.sin((longitudeA-longitudeB)/2), 2));
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a)); 
        int result = (int)Math.ceil(c*R);
        return result;
    }

    @Override
    public List<String> bestRoute(String airportA, String airportB) {
        // TODO Auto-generated method stub
        return graph.shortestPath(airportA, airportB);
    }

    @Override
    public List<String> findAirport(String state) {
        // TODO Auto-generated method stub
        List<String> airportsName = new LinkedList<>();
        for(int i = 0; i <airports.size(); i++){
            if(airports.get(i).getStateName().equals(state)){
                airportsName.add(airports.get(i).getAirportName());
            }
        }
        return airportsName;
    }

    @Override
    public boolean containAirport(AirportInterface airportInterface) {
        // TODO Auto-generated method stub
        return this.airportInterface.equals(airportInterface);
    }

    @Override
    public void addAirport(AirportInterface airpot) {
        // TODO Auto-generated method stub
        airports.add(airpot);
    }

    @Override
    public boolean edgeExits(String airportA, String airportB) {
        // TODO Auto-generated method stub
        if(calculateDistance(airportA, airportB) <= airplantLimitDistance){
            return true;
        }
        return false;
    }
    
    @Override
    public void drawGraph(){
        graph = new CS400Graph<>();

        for(int i = 0; i < airports.size(); i++){
            String airport = airports.get(i).getAirportName();
            graph.insertVertex(airport);
        }

        for(int i = 0; i < airports.size() - 1; i++){
            for(int j = i + 1; j < airports.size(); j++){
                String airportA = airports.get(i).getAirportName();
                String airportB = airports.get(j).getAirportName();
                if(edgeExits(airportA, airportB )){
                    int distance = calculateDistance(airportA, airportB);
                    graph.insertEdge(airportA, airportB, distance);
                    graph.insertEdge(airportB, airportA, distance);
                }
            }
        }
    }

    @Override
    public int calculateStopNumber(String airportA, String airportB) {
        // TODO Auto-generated method stub
        List<String> path = bestRoute(airportA, airportB);
        return path.size() - 2;
    }

    @Override
    public int calculateTripDistance(String airportA, String airportB) {
        // TODO Auto-generated method stub
        return graph.getPathCost(airportA, airportB);
    }

    public CS400Graph<String> getGraph(){
        return graph;
    }
}




// Placeholder
class LASBackEndPlaceholder implements LASBackEndInterface {
    private AirportInterface airportInterface;

    @Override
    public int calculateDistance(String airportA, String airportB) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean edgeExits(String airportA, String airportB) {
        return false;
    }

    @Override
    public List<String> bestRoute(String airportA, String airportB) {
        // TODO Auto-generated method stub
        List<String> route = new LinkedList<>();
        
        return route;
    }

    @Override
    public List<String> findAirport(String state) {
        // TODO Auto-generated method stub
        List<String> airports = new LinkedList<>();
        if(airportInterface.getStateName().equals(state)){
            airports.add(airportInterface.getAirportName());
        }
        return airports;
    }

    @Override
    public boolean containAirport(AirportInterface airportInterface) {
        // TODO Auto-generated method stub
        return this.airportInterface.equals(airportInterface);
    }

    @Override
    public void addAirport(AirportInterface airport) {
        // TODO Auto-generated method stub
        this.airportInterface  = airport; 
    }

    @Override
    public int calculateStopNumber(String airportA, String airportB) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void drawGraph() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int calculateTripDistance(String airportA, String airportB) {
        // TODO Auto-generated method stub
        return 0;
    }

}