package org.example.taxiuber.domain;

public class RideRequest {
    private Passenger passenger;
    private double fromX;
    private double fromY;
    private double toX;
    private double toY;

    public RideRequest(Passenger passenger) {
        this.passenger = passenger;
        this.fromX = passenger.getX();
        this.fromY = passenger.getY();
        this.toX = passenger.getDestX();
        this.toY = passenger.getDestY();
    }

    public Passenger getPassenger() { return passenger; }
    public double getFromX() { return fromX; }
    public double getFromY() { return fromY; }
    public double getToX() { return toX; }
    public double getToY() { return toY; }

    @Override
    public String toString() {
        return String.format("RideRequest{passenger=%s, from=(%.1f, %.1f), to=(%.1f, %.1f)}",
                passenger.getName(), fromX, fromY, toX, toY);
    }
}
