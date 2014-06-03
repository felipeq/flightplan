package pl.areusmart.flightplan;

import java.text.DecimalFormat;

class ListItem implements Comparable<ListItem> {
    private Airport airport;
    private Double value;

    public ListItem(Airport _airport, Double _value) {
        airport = _airport;
        value = _value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public int compareTo(ListItem o) {
        if (value < o.getValue())
            return -1;
        if (value > o.getValue())
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return airport.toString();
    }
}
