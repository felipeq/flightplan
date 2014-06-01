package pl.areusmart.flightplan;

public class LineFunction {
    Point Start;
    Point End;
    double A;
    double C;
    double Length;
    LineFunction PerpendicularThroughStart;
    LineFunction PerpendicularThroughEnd;

    public Point getStart() {
        return Start;
    }

    public Point getEnd() {
        return End;
    }

    public double getLength() {
        return Length;
    }

    public LineFunction getPerpendicularThroughStart() {
        return PerpendicularThroughStart;
    }

    public LineFunction getPerpendicularThroughEnd() {
        return PerpendicularThroughEnd;
    }

    public LineFunction(Point start, Point end) {
        Start = start;
        End = end;
        Length=DistanceBetween(Start,End);
        double a1 = Start.getY() - End.getY();
        double a2 = Start.getX() - End.getX();
        A = -(a1 / a2);
        C = -(Start.getY() - (-A * Start.getX()));
        PerpendicularThroughStart = getPerpendicular(Start);
        PerpendicularThroughEnd = getPerpendicular(End);
    }

    public LineFunction(double StartX, double StartY, double _A) {
        Start = new Point(StartX, StartY);
        A = _A;
        C = -(Start.getY() + (A * Start.getX()));
    }

    public double DistanceTo(Point p) {
        double top = Math.abs(A * p.getX() + p.getY() + C);
        double bottom = Math.sqrt(A * A + C * C);
        return top / bottom;
    }
    public double DistanceBetween(Point start,Point end){
        double left = Math.pow((start.getX() - end.getX()), 2);
        double right = Math.pow((start.getY() - end.getY()), 2);
        return Math.sqrt(left + right);
    }
    public double getC() {
        return C;
    }

    public double getA() {
        return A;
    }
    public double get_b() {
        return -C;
    }

    public double get_a() {
        return -A;
    }
     public LineFunction getPerpendicular(Point p) {
        return new LineFunction(p.getX(), p.getY(), -(1.0 / A));
    }

    public double getArg(double y)
    {
        return (y-get_b())/get_a();
    }
    public double getValue(double x) {
        return (-A) * x - C;
    }

    public boolean IsAboveOrOn(Point p) {
        if (getValue(p.getX()) <= p.getY())
            return true;
        else
            return false;
    }

    public boolean IsBelowOrOn(Point p) {
        if (getValue(p.getX()) >= p.getY())
            return true;
        else
            return false;
    }
}
