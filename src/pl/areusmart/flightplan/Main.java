package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Zasada działania algorytmu:
 * 1. Pobieramy z bazy lotnisko Startowe i Końcowe.
 * 2. Wyznaczamy prostą pomiędzy tymi punktami.
 * 3. Wyznaczamy 2 proste prostopadłe do ścieżki lotu, przechodzące przez punkt startowy i końcowy.
 * Wydzieliliśmy 3 obszary: jeden zawiera wszystkie punkty mniejsze niż punkt startowy,
 * drugi zawiera punkty pomiędzy startem a końcem, trzeci znajdujące się za końcem.
 * 4. Pobieramy lotniska z bazy i liczymy odległość od ścieżki lotu zależnie od obszaru,
 * na którym jest dane lotnisko. Dla 1 obszaru jest to najkrótsza odległość pomiędzy lotniskiem na startem,
 * dla drugiego odległość od ścieżki lotu, dla trzeciego odległość lotniska od punktu końcowego.
 * 5. Znajdujemy punkt przecięcia prostej prostopadłej do ścieżki lotu przechodzącej przez wybrane lotnisko.
 * Znajdujemy punkt wspólny obu prostych, liczymy jego odległość od Startu i otrzymujemy % przebytej trasy.
 * 6. Obliczamy czas korzystając z prędkości, odległości i % przebytej trasy.
 * <p/>
 * UWAGI:
 * Z bazy danych wyrzuciłem lotniska bez IATA. Chciałem też wyrzucić wszystkie lotniska, dla których
 * Daylight savings time jest inne niż "E" lub "U" ( bo niektóre europejskie lotniska też mają "U"),
 * ale może się zdarzyć, że lot na terenie Europy będzie miał lotniska zapasowe z innego kontynentu i
 * wtedy output byłby niekompletny.
 * <p/>
 * Dodatkowo nie wiedziałem, co zrobić z lotniskami, które się znajdują ZA lotniskami startowymi i początkowymi,
 * bo np. przy locie KRK - MMX Rzeszów znajduje się za Krakowem w stosunku do ścieżki lotu, ale w razie
 * awarii samolotu np. tuż po starcie, gdyby lotnisko w Krakowie nie mogło przyjąć samolotu, to jednym z najbliższych
 * zapasowych lotnisk jest to w Rzeszowie. Dlatego też czas przelotu nad lotniskiem dla lotnisk ze strefy 1 & 3
 * ustawiłem na czas startu lub lądowania, bo samolot znajduje się najbliżej tych punktów w momencie startu lub lądowania.
 * Zaimplementowałem flagę <code>ExtendedSearch</code>, która może wyłączyć sektory 1 & 3 z poszukiwań.
 */
public class Main {

    public static final int DegreeInKm = 111;
    final static String PathToData = "lotniska.dat";


    /**
     * Czy 1 i 3 sektor ma brać udział w obliczeniach?
     */
    private static boolean ExtendedSearch = false;

    /**
     * Getting all airports from <code>PathToData</code>
     *
     * @return Full list of airports
     */
    public static ArrayList<Airport> FindAirportsInDb() {
        return FindAirportsInDb(null);
    }

    /**
     * Getting specific airports from <code>PathToData</code>
     *
     * @param IATAs airports IATAs we want to find in db
     * @return Full list of airports
     */
    public static ArrayList<Airport> FindAirportsInDb(String[] IATAs) {
        BufferedReader br = null;

        ArrayList<Airport> airports = new ArrayList<Airport>();
        try {
            String Line;
            br = new BufferedReader(new FileReader(PathToData));

            while ((Line = br.readLine()) != null) {
                if (IATAs != null) {
                    if (airports.size() == 2)
                        return airports;
                    Airport ap = new Airport(Line);
                    for (String s : IATAs) {
                        if (ap.getIATA().equals(s))
                            airports.add(ap);
                    }

                } else {
                    airports.add(new Airport(Line));
                }
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

    public static void main(String[] args) throws ParseException {
        Airport StartAirport;
        Airport EndAirport;

        ArrayList<Airport> FirstAirports = FindAirportsInDb(new String[]{args[0], args[1]});
        if (FirstAirports.get(0).getIATA().equals(args[0])) {
            StartAirport = FirstAirports.get(0);
            EndAirport = FirstAirports.get(1);
        } else {
            StartAirport = FirstAirports.get(1);
            EndAirport = FirstAirports.get(0);
        }
        double MaxDistanceFromFlyPath = Double.parseDouble(args[2]) / DegreeInKm;
        double Velocity = Double.parseDouble(args[3]) / DegreeInKm;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        cal.setTime(sdf.parse(args[4]));

        ArrayList<ListItem> Results = new ArrayList<ListItem>();
        LineFunction FlyPath = new LineFunction(StartAirport.getCoords(), EndAirport.getCoords());

        double TotalAirtime = 3600 * FlyPath.getLength() / Velocity;

        for (Airport airport : FindAirportsInDb()) {
            switch (FlyPath.NumberOfSectorFor(airport)) {
                case 1: {
                    if (ExtendedSearch == true) {
                        if (FlyPath.isStartLower()) {
                            double DistToStartPoint = FlyPath.DistanceBetween(airport.getCoords(), FlyPath.getStart());
                            if (DistToStartPoint <= MaxDistanceFromFlyPath)
                                if (!(airport.getIATA().equals(StartAirport.getIATA()) || airport.getIATA().equals(EndAirport.getIATA())))
                                    Results.add(new ListItem(airport, 0.0));
                        } else {
                            double DistToStartPoint = FlyPath.DistanceBetween(airport.getCoords(), FlyPath.getEnd());
                            if (DistToStartPoint <= MaxDistanceFromFlyPath)
                                if (!(airport.getIATA().equals(StartAirport.getIATA()) || airport.getIATA().equals(EndAirport.getIATA())))
                                    Results.add(new ListItem(airport, 1.0));
                        }
                    }
                    break;
                }
                case 2: {
                    double DistToFlyPath = FlyPath.DistanceTo(airport.getCoords());
                    if (DistToFlyPath <= MaxDistanceFromFlyPath)
                        if (!(airport.getIATA().equals(StartAirport.getIATA()) || airport.getIATA().equals(EndAirport.getIATA()))) {
                            Point cm = CommonPoint(FlyPath, FlyPath.getPerpendicular(airport.getCoords()));
                            double length = FlyPath.DistanceBetween(cm) / FlyPath.getLength();
                            Results.add(new ListItem(airport, length));
                        }
                    break;
                }
                case 3: {
                    if (ExtendedSearch == true) {
                        if (FlyPath.isStartLower()) {
                            double DistToEndPoint = FlyPath.DistanceBetween(airport.getCoords(), FlyPath.getEnd());
                            if (DistToEndPoint <= MaxDistanceFromFlyPath)
                                if (!(airport.getIATA().equals(StartAirport.getIATA()) || airport.getIATA().equals(EndAirport.getIATA())))
                                    Results.add(new ListItem(airport, 1.0));
                        } else {
                            double DistToEndPoint = FlyPath.DistanceBetween(airport.getCoords(), FlyPath.getStart());
                            if (DistToEndPoint <= MaxDistanceFromFlyPath)
                                if (!(airport.getIATA().equals(StartAirport.getIATA()) || airport.getIATA().equals(EndAirport.getIATA())))
                                    Results.add(new ListItem(airport, 0.0));
                        }
                    }
                    break;
                }
            }
        }
        Collections.sort(Results);
        Results.add(0, new ListItem(StartAirport, 0.0));
        Results.add(Results.size(), new ListItem(EndAirport, 1.0));
        for (ListItem li : Results) {
            int Czas = (int) (TotalAirtime * li.getValue());
            Calendar temp = Calendar.getInstance();
            temp.setTime(cal.getTime());
            temp.add(Calendar.SECOND, Czas);
            System.out.println(li.toString() + " " + sdf.format(temp.getTime()));
        }
    }

    /**
     * Finding common point of 2 line functions
     *
     * @param a First line
     * @param b Second line
     * @return Common point
     */
    public static Point CommonPoint(LineFunction a, LineFunction b) {
        double top = b.get_b() - a.get_b();
        double bottom = a.get_a() - b.get_a();
        double x = top / bottom;
        double y = a.getValue(x);
        return new Point(x, y);
    }
}
