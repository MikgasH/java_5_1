package org.example.taxiuber.domain;

import org.example.taxiuber.state.TaxiFreeState;
import org.example.taxiuber.state.TaxiState;

public class Taxi {
    private int id;
    private double x;
    private double y;
    private TaxiState currentState;

    public Taxi(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.currentState = new TaxiFreeState();
    }

    public int getId() { return id; }
    public double getX() { return x; }
    public double getY() { return y; }

    public TaxiState getCurrentState() { return currentState; }
    public void setCurrentState(TaxiState st) { currentState = st; }

    public void assignRide(RideRequest req) {
        currentState.assignPassenger(this, req);
    }
    public void finishRide() {
        currentState.finishRide(this);
    }

    @Override
    public String toString() {
        return String.format("Taxi{id=%d, (%.1f, %.1f), state=%s}",
                id, x, y, currentState.getClass().getSimpleName());
    }
}
