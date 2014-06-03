package pl.areusmart.flightplan;

public class Point {
    private double X;
    private double Y;

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
