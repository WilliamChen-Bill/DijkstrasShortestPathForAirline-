
// interface (implemented with proposal)

interface AirportInterface {
    public String getStateName();
    public String getAirportName();
    public double getLatitude();
    public double getLongitude();
}

// public class (implemented primarilly in final app week)

public class Airport implements AirportInterface {
    private String stateName;
    private String airportName;
    private double latitude;
    private double longitude;

    public Airport(String sName, String aName, double la, double lo) {
        this.stateName = sName;
        this.airportName = aName;
        this.latitude = la;
        this.longitude = lo;
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    @Override
    public String getAirportName() {
        return airportName;
    }
    
    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
}

// placeholder(s) (implemented with proposal, and possibly added to later)
class AirportPlaceholderA implements AirportInterface {
    @Override
    public String getStateName() {
        return "Illinois";
    }

    @Override
    public String getAirportName() {
        return "Springfield";
    }
    
    @Override
    public double getLatitude() {
        return 39.798363;
    }

    @Override
    public double getLongitude() {
        return -89.654961;
    }
}

class AirportPlaceholderB implements AirportInterface {
    @Override
    public String getStateName() {
        return "Michigan";
    }

    @Override
    public String getAirportName() {
        return "Lansing";
    }
    
    @Override
    public double getLatitude() {
        return 42.733635;
    }

    @Override
    public double getLongitude() {
        return -84.555328;
    }
}

class AirportPlaceholderC implements AirportInterface {
    @Override
    public String getStateName() {
        return "Wisconsin";
    }

    @Override
    public String getAirportName() {
        return "Madison";
    }
    
    @Override
    public double getLatitude() {
        return 43.074684;
    }

    @Override
    public double getLongitude() {
        return -89.384445;
    }
}
