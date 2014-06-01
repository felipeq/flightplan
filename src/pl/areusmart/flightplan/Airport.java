package pl.areusmart.flightplan;

import java.text.DecimalFormat;

/**
 * Created by Filip on 2014-05-28.
 */
public class Airport {
    String Name;
    String City;
    String Country;
    String IATA;
    double Latitude;
    double Longitude;
    char RegionCode;
    Point coords;

    public String getName() {
        return Name;
    }

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
        //1,"Goroka","Goroka","Papua New Guinea","START","AYGA",0,0,5282,10,"U"
        String[] SplitInfo = LineFrom_airportsdat.split("\"");
//        Name = SplitInfo[1].substring(1, SplitInfo[1].length() - 1);
//        City = SplitInfo[2].substring(1, SplitInfo[2].length() - 1);
//        Country = SplitInfo[3].substring(1, SplitInfo[3].length() - 1);
//        IATA = SplitInfo[4].substring(1, SplitInfo[4].length() - 1);
//        Latitude = Double.parseDouble(SplitInfo[6]);
//        Longitude = Double.parseDouble(SplitInfo[7]);
//        RegionCode=SplitInfo[10].charAt(1);
        try {
        Name = SplitInfo[1];
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

    public char getRegionCode() {
        return RegionCode;
    }

    @Override
    public String toString() {
        //KRK Krakow Poland 50.0777 19.7848
        DecimalFormat df = new DecimalFormat("#.0000");
        return IATA+" "+City+" "+Country+" "+df.format(Latitude)+" "+df.format(Longitude);
    }

}
