package org.example.taxiuber.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taxiuber.domain.RideRequest;
import org.example.taxiuber.domain.Taxi;

public class TaxiFreeState implements TaxiState {
    private static final Logger logger = LogManager.getLogger(TaxiFreeState.class);

    @Override
    public void assignPassenger(Taxi taxi, RideRequest request) {
        logger.info("Taxi {}: assigned to passenger {} (switching to ON WAY)",
                taxi.getId(), request.getPassenger().getName());
        taxi.setCurrentState(new TaxiOnWayState());
    }

    @Override
    public void finishRide(Taxi taxi) {
        logger.warn("Taxi {} is FREE, no passenger to finish.", taxi.getId());
    }
}
