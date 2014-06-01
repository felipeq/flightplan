package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static final int DegreeInKm = 111;

    public static ArrayList<Airport> FindAllAirportsInDb() {
        BufferedReader br = null;
        ArrayList<Airport> airports = new ArrayList<Airport>();
        try {
            String Line;
            String PathToData = "airports.dat";
            Airport airport;
            br = new BufferedReader(new FileReader(PathToData));

            while ((Line = br.readLine()) != null) {
                airports.add(new Airport(Line));
            }
            return airports;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return null;
    }

    public static Airport FindAirportInDb(String IATA) {
        BufferedReader br = null;

        try {
            String Line;
            String PathToData = "airports.dat";
            Airport airport;
            br = new BufferedReader(new FileReader(PathToData));

            while ((Line = br.readLine()) != null) {
                airport = new Airport(Line);
                if (airport.getIATA().equals(IATA))
                    return airport;
                if (IATA.isEmpty())
                    return airport;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return null;
    }

    public static void main(String[] args) throws ParseException {

        int i = 0;
        for (String s : args)
            System.out.println((i++) + ":" + s);
        System.out.println("===================================================");
        Airport StartAirport = FindAirportInDb(args[0]);
        Airport EndAirport = FindAirportInDb(args[1]);
        double MaxDistanceFromFlyPath = Double.parseDouble(args[2]);
        double Velocity = Double.parseDouble(args[3]);

        Date date = new SimpleDateFormat("HH:mm:ss").parse(args[4]);


        System.out.println(StartAirport.toString());
        System.out.println(EndAirport.toString());
        System.out.println(MaxDistanceFromFlyPath);
        System.out.println(Velocity);
        System.out.println(date);

        // lat na y, long na x
        LineFunction FlyPath = new LineFunction(StartAirport.getLatitude(), StartAirport.getLongitude(), EndAirport.getLatitude(), EndAirport.getLongitude());
        for (Airport airport : FindAllAirportsInDb()) {
            double DistanceToFlyPath = FlyPath.DistanceTo(airport.getLatitude(),airport.getLongitude());
            double DistanceToStart = FlyPath.DistanceToStart(airport.getLatitude(),airport.getLongitude());
            double DistanceToEnd = FlyPath.DistanceToEnd(airport.getLatitude(),airport.getLongitude());
            if (DistanceToFlyPath <= MaxDistanceFromFlyPath)
                //if (DistanceToStart<=MaxDistanceFromFlyPath || DistanceToEnd<=MaxDistanceFromFlyPath)
                System.out.println(DistanceToFlyPath + " - " + airport);
        }
    }
}
