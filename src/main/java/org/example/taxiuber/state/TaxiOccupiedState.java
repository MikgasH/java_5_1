package org.example.taxiuber.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taxiuber.domain.RideRequest;
import org.example.taxiuber.domain.Taxi;

public class TaxiOccupiedState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(TaxiOccupiedState.class);

    @Override
    public void assignPassenger(Taxi taxi, RideRequest request) {
        logger.warn("Taxi {} is OCCUPIED, can't assign a new passenger!", taxi.getId());
    }

    @Override
    public void finishRide(Taxi taxi) {
        logger.info("Taxi {}: ride finished, switching to FREE state", taxi.getId());
        taxi.setCurrentState(new TaxiFreeState());
    }
}
