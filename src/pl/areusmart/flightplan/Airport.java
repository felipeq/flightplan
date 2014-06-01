package pl.areusmart.flightplan;

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

    public Airport(String name, String city, String country, String IATA, double latitude, double longitude) {
        Name = name;
        City = city;
        Country = country;
        this.IATA = IATA;
        Latitude = latitude;
        Longitude = longitude;

    }

    public Airport(String LineFrom_airportsdat) {
        String[] SplitInfo = LineFrom_airportsdat.split(",");
        if (SplitInfo.length > 11)
            return;
        Name = SplitInfo[1].substring(1, SplitInfo[1].length() - 1);
        City = SplitInfo[2].substring(1, SplitInfo[2].length() - 1);
        Country = SplitInfo[3].substring(1, SplitInfo[3].length() - 1);
        IATA = SplitInfo[4].substring(1, SplitInfo[4].length() - 1);
        Latitude = Double.parseDouble(SplitInfo[6]);
        Longitude = Double.parseDouble(SplitInfo[7]);
        RegionCode=SplitInfo[10].charAt(1);

    }

    public char getRegionCode() {
        return RegionCode;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "Name='" + Name + '\'' +
                ", City='" + City + '\'' +
                ", Country='" + Country + '\'' +
                ", IATA='" + IATA + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }

}
