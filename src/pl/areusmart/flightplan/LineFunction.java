package pl.areusmart.flightplan;

public class LineFunction {
    Point Start;
    Point End;
    double A;
    double C;
    double Length;
    LineFunction LowerPerpendicular;
    LineFunction HigherPerpendicular;
boolean StartLower =true;
    public Point getStart() {
        return Start;
    }

    public Point getEnd() {
        return End;
    }

    public double getLength() {
        return Length;
    }

    public LineFunction(Point start, Point end) {
        Start = start;
        End = end;
        Length = DistanceBetween(Start, End);
        double a1 = Start.getY() - End.getY();
        double a2 = Start.getX() - End.getX();
        A = -(a1 / a2);
        C = -(Start.getY() - (-A * Start.getX()));
        LowerPerpendicular = getPerpendicular(Start);
        HigherPerpendicular = getPerpendicular(End);
        // Sprawdź, czy nie są odwrotnie ustawione
        if (LowerPerpendicular.getValue(0)> HigherPerpendicular.getValue(0))
        {
            StartLower =false;
            LineFunction temp= LowerPerpendicular;
            LowerPerpendicular = HigherPerpendicular;
            HigherPerpendicular =temp;
        }
    }

    public boolean isStartLower() {
        return StartLower;
    }

    public LineFunction(double StartX, double StartY, double _A) {
        Start = new Point(StartX, StartY);
        A = _A;
        C = -(Start.getY() + (A * Start.getX()));
    }

    /**
     * Shortest path from p to this LineFunction
     * @param p
     * @return
     */
    public double DistanceTo(Point p) {
        double top = Math.abs(A * p.getX() + p.getY() + C);
        double bottom = Math.sqrt(A * A + 1);
        return top / bottom;
    }

    // potrzebne do pointów
    public static double DistanceBetween(Point start, Point end) {
        double left = Math.pow((start.getX() - end.getX()), 2);
        double right = Math.pow((start.getY() - end.getY()), 2);
        return Math.sqrt(left + right);
    }
    public double DistanceBetween(Point start)
    {
        return DistanceBetween(start,this.getStart());
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

    public double getArg(double y) {
        return (y - get_b()) / get_a();
    }

    public double getValue(double x) {
        return (-A) * x - C;
    }

    public boolean IsAboveOrOn(Point p) {
        return getValue(p.getX()) <= p.getY();
    }

    public boolean IsBelowOrOn(Point p) {
        return getValue(p.getX()) >= p.getY();
    }
    public int NumberOfSectorFor(Airport ap){
        if (LowerPerpendicular.IsBelowOrOn(ap.getCoords()))
            return 1;
        if (HigherPerpendicular.IsAboveOrOn(ap.getCoords()))
            return 3;
        else
            return 2;
    }
}
