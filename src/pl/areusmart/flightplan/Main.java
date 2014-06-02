package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class ListItem implements Comparable<ListItem> {
    private Airport airport;
    private Double value;

    public ListItem(Airport _airport, Double _value) {
        airport = _airport;
        value = _value;
    }

    public Airport getAirport() {
        return airport;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public int compareTo(ListItem o) {
        if (o.getValue().equals(0.0) && !o.getAirport().equals(Main.StartAirport))
            return -1;
        if (o.getValue().equals(1.0) && !o.getAirport().equals(Main.EndAirport))
            return 1;
        if (value < o.getValue())
            return -1;
        if (value > o.getValue())
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return airport.getIATA() + " " + airport.getCity() + " " + airport.getCountry() + " " + df.format(airport.getLatitude()) + " " + df.format(airport.getLongitude()) + " " + getValue();
    }
}

public class Main {
    public static final int DegreeInKm = 111;
    public static Airport StartAirport;
    public static Airport EndAirport;
    final static String PathToData = "test.dat";

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
        StartAirport = FindAirportInDb(args[0]);
        EndAirport = FindAirportInDb(args[1]);
        double MaxDistanceFromFlyPath = Double.parseDouble(args[2]);
        double Velocity = Double.parseDouble(args[3]) / DegreeInKm;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        cal.setTime(sdf.parse(args[4]));

        Map<Double, Airport> wyniki = new TreeMap<Double, Airport>();
        ArrayList<ListItem> lista = new ArrayList<ListItem>();

        ArrayList<Airport> results = new ArrayList<Airport>();
        ArrayList<Double> punkty = new ArrayList<Double>();
        LineFunction FlyPath = new LineFunction(StartAirport.getCoords(), EndAirport.getCoords());
        double TotalAirtime = FlyPath.getLength() / Velocity;
        for (Airport airport : FindAllAirportsInDb()) {
            if (FlyPath.PerpendicularThroughStart.IsAboveOrOn(airport.getCoords())) {
                if (FlyPath.PerpendicularThroughEnd.IsBelowOrOn(airport.getCoords())) {
                    if (FlyPath.DistanceTo(airport.getCoords()) <= MaxDistanceFromFlyPath) {

                        Point cm = (CommonPoint(FlyPath, FlyPath.getPerpendicular(airport.getCoords())));
                        double length = FlyPath.DistanceBetween(FlyPath.getStart(), cm) / FlyPath.getLength();
                        punkty.add(length);
                        wyniki.put(length, airport);
                        lista.add(new ListItem(airport, length));
                        results.add(airport);
                    }
                } else {
                    if (FlyPath.PerpendicularThroughEnd.DistanceBetween(airport.getCoords(), FlyPath.getEnd()) <= MaxDistanceFromFlyPath) {
                        // Jest nad Endem, więc najbliżej będzie jak doleci
                        double length = 1.0;
                        punkty.add(length);
                        wyniki.put(length, airport);
                        lista.add(new ListItem(airport, length));
                        results.add(airport);
                    }
                }
            } else {
                if (FlyPath.PerpendicularThroughStart.DistanceBetween(airport.getCoords(), FlyPath.Start) <= MaxDistanceFromFlyPath) {
                    Point cm = (CommonPoint(FlyPath, FlyPath.getPerpendicular(airport.getCoords())));
                    double length = FlyPath.DistanceBetween(FlyPath.getStart(), cm) / FlyPath.getLength();
                    punkty.add(length);
                    wyniki.put(length, airport);
                    lista.add(new ListItem(airport, length));
                    results.add(airport);
                }
            }
        }
        Collections.sort(lista);
        for (ListItem li : lista) {
            //int Czas = (int) (TotalAirtime * li.getValue() * 3600);
            //Calendar temp = Calendar.getInstance();
            //temp.add(Calendar.SECOND, Czas);
            //System.out.println(li.toString() + " " + sdf.format(temp.getTime()));
            System.out.println(li.toString());
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
