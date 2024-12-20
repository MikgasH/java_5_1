package org.example.taxiuber.service.dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taxiuber.domain.RideRequest;
import org.example.taxiuber.domain.Taxi;
import org.example.taxiuber.service.singleton.SafeSingleton;
import org.example.taxiuber.service.threadpool.CustomThreadPool;
import org.example.taxiuber.utils.AppConstant;
import org.example.taxiuber.utils.DistanceCalculator;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Dispatcher {
    private static final Logger logger = LogManager.getLogger(Dispatcher.class);

    private final List<Taxi> taxis;
    private final CustomThreadPool pool;
    private final SafeSingleton orderCounter = SafeSingleton.getInstance();

    public Dispatcher(List<Taxi> taxis, CustomThreadPool pool) {
        this.taxis = taxis;
        this.pool = pool;
    }

    public void dispatchRequest(RideRequest request) {
        logger.info("Dispatcher received request: {}", request);

        long orderId = orderCounter.getNextOrderId();
        List<Taxi> nearbyTaxis = findTaxisInRadius(request);

        if (nearbyTaxis.isEmpty()) {
            logger.warn(AppConstant.NO_AVAILABLE_TAXIS, AppConstant.TAXI_SEARCH_RADIUS, request);
            return;
        }

        Taxi chosen = findBestTaxi(nearbyTaxis, request);

        if (chosen == null) {
            logger.warn(AppConstant.NO_SUITABLE_TAXI_FOUND, request);
            return;
        }

        Callable<Void> task = () -> {
            logger.info(AppConstant.ORDER_ASSIGNED,
                    orderId, chosen.getId(), request.getPassenger().getName());

            chosen.assignRide(request);

            double distToPassenger = DistanceCalculator.calculateDistance(
                    chosen.getX(), chosen.getY(),
                    request.getFromX(), request.getFromY());
            long sec1 = (long) Math.max(1, distToPassenger);
            TimeUnit.SECONDS.sleep(sec1);

            chosen.finishRide();

            double distToDest = DistanceCalculator.calculateDistance(
                    request.getFromX(), request.getFromY(),
                    request.getToX(), request.getToY());
            long sec2 = (long) Math.max(1, distToDest);
            TimeUnit.SECONDS.sleep(sec2);

            chosen.finishRide();

            logger.info(AppConstant.ORDER_COMPLETED,
                    orderId, chosen.getId(), request.getPassenger().getName());
            return null;
        };

        pool.submit(task);
    }

    private List<Taxi> findTaxisInRadius(RideRequest req) {
        List<Taxi> nearbyTaxis = taxis.stream()
                .filter(t -> DistanceCalculator.calculateDistance(
                        t.getX(), t.getY(), req.getFromX(), req.getFromY()) <= AppConstant.TAXI_SEARCH_RADIUS)
                .collect(Collectors.toList());

        logger.info(AppConstant.TAXIS_FOUND_IN_RADIUS,
                nearbyTaxis.size(), AppConstant.TAXI_SEARCH_RADIUS, req.getPassenger().getName());

        return nearbyTaxis;
    }

    private Taxi findBestTaxi(List<Taxi> nearbyTaxis, RideRequest req) {
        double bestDist = Double.MAX_VALUE;
        Taxi best = null;
        for (Taxi t : nearbyTaxis) {
            double d = DistanceCalculator.calculateDistance(
                    t.getX(), t.getY(), req.getFromX(), req.getFromY());
            if (d < bestDist) {
                bestDist = d;
                best = t;
            }
        }
        return best;
    }
}
