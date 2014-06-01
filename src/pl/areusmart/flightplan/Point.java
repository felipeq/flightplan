package pl.areusmart.flightplan;

/**
 * Created by Filip on 2014-05-28.
 */
public class Point {
    double X;
    double Y;

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }

    Point(double x, double y) {

        X = x;
        Y = y;
    }
}
