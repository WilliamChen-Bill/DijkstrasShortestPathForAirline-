import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;

// interface (implemented with proposal)

interface AirportDataLoaderInterface {
    public List<AirportInterface> loadFile(String csvFilePath) throws FileNotFoundException;
}

// public class (implemented primarilly in final app week)

public class AirportDataLoader implements AirportDataLoaderInterface {

    @Override
    public List<AirportInterface> loadFile(String csvFilePath) throws FileNotFoundException {
        // check file
        File csvfile = new File(csvFilePath);
        if(!csvfile.exists() || !csvfile.isFile())
            throw new FileNotFoundException("File not found");
        
        // read file
        List<AirportInterface> list = new LinkedList<>();
        String line;
        String split = ",";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), "UTF-8"));
            
            while((line = reader.readLine()) != null) {
                String[] entireLine = line.split(split);
                String sName = entireLine[0];
                String aName = entireLine[1];
                double latitude = Double.parseDouble(entireLine[2]);
                double longitude = Double.parseDouble(entireLine[3]);
                AirportInterface airport = new Airport(sName, aName, latitude, longitude);
                list.add(airport);
            }
            reader.close();
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}

// placeholder(s) (implemented with proposal, and possibly added to later)
class AirportDataLoaderPlaceholder implements AirportDataLoaderInterface {
    public List<AirportInterface> loadFile(String csvFilePath) throws FileNotFoundException{
        List<AirportInterface> list = new LinkedList<>();
        list.add(new AirportPlaceholderA());
        list.add(new AirportPlaceholderB());
        return list;
    }
    public List<AirportInterface> loadAllFilesInDirectory(String directoryPath) throws FileNotFoundException{
        List<AirportInterface> list = new LinkedList<>();
        list.add(new AirportPlaceholderA());
        list.add(new AirportPlaceholderB());
        list.add(new AirportPlaceholderC());
        return list;
    }
}
