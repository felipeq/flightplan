package pl.areusmart.flightplan;

public class LineFunction {
    private Point Start;
    private Point End;
    private double A;
    private double C;
    private double Length;
    private LineFunction LowerPerpendicular;
    private LineFunction HigherPerpendicular;
    /**
     * Zamiana miejscami startu z końcem ( tzn. z KRK MMX na MMX KRK ) powoduje, że proste
     * prostopadłe powinny zamienić się miejscami, żeby warunek isAbove i isBelow miał sens.
     * Ta flaga informuje o zmianie.
     */
    private boolean StartLower = true;
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
     * Distance between calling line function and p
     * @param p point
     * @return Distance between point and line function
     */
    public double DistanceTo(Point p) {
        double top = Math.abs(A * p.getX() + p.getY() + C);
        double bottom = Math.sqrt(A * A + 1);
        return top / bottom;
    }

    /**
     * Distance between 2 points
     *
     * @param start first point
     * @param end   second point
     * @return distance between them
     */
    public static double DistanceBetween(Point start, Point end) {
        double left = Math.pow((start.getX() - end.getX()), 2);
        double right = Math.pow((start.getY() - end.getY()), 2);
        return Math.sqrt(left + right);
    }

    public double DistanceBetween(Point start)
    {
        return DistanceBetween(start,this.getStart());
    }

    public double get_b() {
        return -C;
    }

    public double get_a() {
        return -A;
    }

    /**
     * Gets perpendicular function to calling function and given point
     * @param p point on function
     * @return perpendicular function to calling function and given point
     */
    public LineFunction getPerpendicular(Point p) {
        return new LineFunction(p.getX(), p.getY(), -(1.0 / A));
    }

    public double getValue(double x) {
        return (-A) * x - C;
    }

    /**
     * Check if given point is above function
     * @param p point
     * @return if given point is above function
     */
    public boolean IsAboveOrOn(Point p) {
        return getValue(p.getX()) <= p.getY();
    }

    /**
     * Check if given point is below function
     * @param p point
     * @return if given point is below function
     */
    public boolean IsBelowOrOn(Point p) {
        return getValue(p.getX()) >= p.getY();
    }

    /**
     * Determine in which sector given airport is
     * @param ap airport to check
     * @return number of sector
     */
    public int NumberOfSectorFor(Airport ap){
        if (LowerPerpendicular.IsBelowOrOn(ap.getCoords()))
            return 1;
        if (HigherPerpendicular.IsAboveOrOn(ap.getCoords()))
            return 3;
        else
            return 2;
    }
}
