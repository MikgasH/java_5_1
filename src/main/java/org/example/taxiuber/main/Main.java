package org.example.taxiuber.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taxiuber.config.DataReader;
import org.example.taxiuber.config.LoggingConfig;
import org.example.taxiuber.domain.Passenger;
import org.example.taxiuber.domain.RideRequest;
import org.example.taxiuber.domain.Taxi;
import org.example.taxiuber.service.dispatcher.Dispatcher;
import org.example.taxiuber.service.threadpool.CustomThreadPool;
import org.example.taxiuber.utils.AppConstant;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        new LoggingConfig();

        DataReader reader = new DataReader(AppConstant.DATA_FILE_PATH);
        ArrayList<Taxi> taxis = reader.loadTaxis();
        ArrayList<Passenger> passengers = reader.loadPassengers();

        logger.info("Loaded {} taxis.", taxis.size());
        logger.info("Loaded {} passengers.", passengers.size());

        CustomThreadPool pool = new CustomThreadPool(AppConstant.THREAD_POOL_SIZE);

        Dispatcher dispatcher = new Dispatcher(taxis, pool);

        for (Passenger p : passengers) {
            RideRequest req = new RideRequest(p);
            dispatcher.dispatchRequest(req);
        }

        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        pool.shutdown();

        logger.info("Application finished.");
    }
}
