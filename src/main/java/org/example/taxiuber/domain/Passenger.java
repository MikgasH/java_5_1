package org.example.taxiuber.domain;

public class Passenger {
    private String name;
    private double x;
    private double y;
    private double destX;
    private double destY;

    public Passenger(String name, double x, double y, double destX, double destY) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
    }

    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getDestX() { return destX; }
    public double getDestY() { return destY; }

    @Override
    public String toString() {
        return String.format("Passenger{name='%s', from=(%.1f, %.1f), to=(%.1f, %.1f)}",
                name, x, y, destX, destY);
    }
}
