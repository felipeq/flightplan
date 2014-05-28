package pl.areusmart.flightplan;

/**
 * Created by Filip on 2014-05-28.
 */
class Point {
    double X;
    double Y;

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    Point(double x, double y) {

        X = x;
        Y = y;
    }
}

public class LineFunction {
    Point Start;
    Point End;
    double fY;

    public LineFunction(double StartX, double StartY, double EndX, double EndY) {
        Start = new Point(StartX, StartY);
        End = new Point(EndX, EndY);
    }

}
