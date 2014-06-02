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
        DecimalFormat df = new DecimalFormat("#0.0000");
        return IATA+" "+City+" "+Country+" "+df.format(Latitude)+" "+df.format(Longitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airport airport = (Airport) o;

        if (Double.compare(airport.Latitude, Latitude) != 0) return false;
        if (Double.compare(airport.Longitude, Longitude) != 0) return false;
        if (RegionCode != airport.RegionCode) return false;
        if (City != null ? !City.equals(airport.City) : airport.City != null) return false;
        if (Country != null ? !Country.equals(airport.Country) : airport.Country != null) return false;
        if (IATA != null ? !IATA.equals(airport.IATA) : airport.IATA != null) return false;
        if (Name != null ? !Name.equals(airport.Name) : airport.Name != null) return false;
        if (coords != null ? !coords.equals(airport.coords) : airport.coords != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Name != null ? Name.hashCode() : 0;
        result = 31 * result + (City != null ? City.hashCode() : 0);
        result = 31 * result + (Country != null ? Country.hashCode() : 0);
        result = 31 * result + (IATA != null ? IATA.hashCode() : 0);
        temp = Double.doubleToLongBits(Latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) RegionCode;
        result = 31 * result + (coords != null ? coords.hashCode() : 0);
        return result;
    }
}
