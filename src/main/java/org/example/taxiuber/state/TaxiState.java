package org.example.taxiuber.state;

import org.example.taxiuber.domain.RideRequest;
import org.example.taxiuber.domain.Taxi;

public interface TaxiState {
    void assignPassenger(Taxi taxi, RideRequest request);
    void finishRide(Taxi taxi);
}
