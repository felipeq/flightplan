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
    double A;
    double B;

    public LineFunction(double StartX, double StartY, double EndX, double EndY) {
        Start = new Point(StartX, StartY);
        End = new Point(EndX, EndY);
        double a1 = StartY - EndY;
        double a2 = StartX - EndX;
        A = -(a1 / a2);
        B = -(EndY - (-A * EndX));
    }
    public double DistanceTo(double x, double y)
    {
        double top=Math.abs(A *x+y+ B);
        double bottom=Math.sqrt(A * A + B * B);
        return top/bottom;
    }
    public double DistanceToStart(double x, double y)
    {
        double left=Math.pow((x-Start.getX()),2);
        double right=Math.pow((y-Start.getY()),2);
        return Math.sqrt(left+right);
    }
    public double DistanceToEnd(double x, double y)
    {
        double left=Math.pow((x-End.getX()),2);
        double right=Math.pow((y-End.getY()),2);
        return Math.sqrt(left+right);
    }

}
