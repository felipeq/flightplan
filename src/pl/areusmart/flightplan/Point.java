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

    public int IsCloserToZeroThan(Point p) {
        Point Zero = new Point(0, 0);
        double dist=LineFunction.DistanceBetween(p, Zero);
        double distZero=LineFunction.DistanceBetween(this, Zero);
        if (dist< distZero)
            return 1;
        if (dist==distZero)
            return 0;
        else
            return -1;
    }
}
