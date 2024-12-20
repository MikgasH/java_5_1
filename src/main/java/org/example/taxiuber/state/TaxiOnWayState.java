package org.example.taxiuber.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taxiuber.domain.RideRequest;
import org.example.taxiuber.domain.Taxi;

public class TaxiOnWayState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(TaxiOnWayState.class);

    @Override
    public void assignPassenger(Taxi taxi, RideRequest request) {
        logger.warn("Taxi {} is ON WAY, can't assign a new passenger!", taxi.getId());
    }

    @Override
    public void finishRide(Taxi taxi) {
        logger.info("Taxi {}: picking up passenger done, switching to OCCUPIED state", taxi.getId());
        taxi.setCurrentState(new TaxiOccupiedState());
    }
}
