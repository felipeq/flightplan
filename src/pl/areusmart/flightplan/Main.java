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

    final static String PathToData = "lotniska.dat";

    public static ArrayList<Airport> FindAllAirportsInDb() {
        BufferedReader br = null;
        ArrayList<Airport> airports = new ArrayList<Airport>();
        try {
            String Line;
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
        Airport StartAirport = FindAirportInDb(args[0]);
        Airport EndAirport = FindAirportInDb(args[1]);
        double MaxDistanceFromFlyPath = Double.parseDouble(args[2]) / DegreeInKm;
        double Velocity = Double.parseDouble(args[3]);

        Date date = new SimpleDateFormat("HH:mm:ss").parse(args[4]);

        ArrayList<Airport> results = new ArrayList<Airport>();
        ArrayList<Double> punkty = new ArrayList<Double>();
        LineFunction FlyPath = new LineFunction(StartAirport.getCoords(), EndAirport.getCoords());
        for (Airport airport : FindAllAirportsInDb()) {
            if (FlyPath.PerpendicularThroughStart.IsAboveOrOn(airport.getCoords())) {
                if (FlyPath.PerpendicularThroughEnd.IsBelowOrOn(airport.getCoords())) {
                    if (FlyPath.DistanceTo(airport.getCoords()) <= MaxDistanceFromFlyPath) {

                        Point cm = (CommonPoint(FlyPath, FlyPath.getPerpendicular(airport.getCoords())));
                        double length = FlyPath.DistanceBetween(FlyPath.getStart(), cm);
                        punkty.add(length / FlyPath.getLength());

                        results.add(airport);
                    }
                } else {
                    if (FlyPath.PerpendicularThroughEnd.DistanceBetween(airport.getCoords(), FlyPath.Start) <= MaxDistanceFromFlyPath) {
                        Point cm = (CommonPoint(FlyPath, FlyPath.getPerpendicular(airport.getCoords())));
                        double length = FlyPath.DistanceBetween(FlyPath.getStart(), cm);
                        punkty.add(length / FlyPath.getLength());

                        results.add(airport);
                    }
                }
            } else {
                if (FlyPath.PerpendicularThroughStart.DistanceBetween(airport.getCoords(), FlyPath.Start) <= MaxDistanceFromFlyPath) {
                    Point cm = (CommonPoint(FlyPath, FlyPath.getPerpendicular(airport.getCoords())));
                    double length = FlyPath.DistanceBetween(FlyPath.getStart(), cm);
                    punkty.add(length / FlyPath.getLength());

                    results.add(airport);
                }
            }
        }
        int i = 0;
        for (Airport ar : results) {
            double val=punkty.get(i++);
            System.out.println(punkty.get(i++).toString() + ": " + ar.toString());
        }
    }

    public static Point CommonPoint(LineFunction a, LineFunction b) {
        double top = b.get_b() - a.get_b();
        double bottom = a.get_a() - b.get_a();
        double x = top / bottom;
        double y = a.getValue(x);
        return new Point(x, y);
    }
}
