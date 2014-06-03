package pl.areusmart.flightplan;

import java.text.DecimalFormat;

public class Airport {
    private String City;
    private String Country;
    private String IATA;
    private double Latitude;
    private double Longitude;
    private Point coords;

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getIATA() {
        return IATA;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public Airport(String LineFrom_airportsdat) {
        String[] SplitInfo = LineFrom_airportsdat.split("\"");
        try {
            City = SplitInfo[3];
            Country = SplitInfo[5];
            IATA = SplitInfo[7];
            Latitude = Double.parseDouble((SplitInfo[10].split(","))[1]);
            Longitude = Double.parseDouble((SplitInfo[10].split(","))[2]);
        } catch (Exception ex) {
            Latitude = Double.parseDouble((SplitInfo[8].split(","))[2]);
            Longitude = Double.parseDouble((SplitInfo[8].split(","))[3]);

        } finally {
        }
        coords = new Point(Latitude, Longitude);
    }

    public Point getCoords() {
        return coords;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.0000");
        String numbers = df.format(Latitude) + " " + df.format(Longitude);
        return IATA + " " + City + " " + Country + " " + numbers;
    }

}
