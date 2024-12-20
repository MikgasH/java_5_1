package org.example.taxiuber.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.taxiuber.domain.Passenger;
import org.example.taxiuber.domain.Taxi;
import org.example.taxiuber.utils.AppConstant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReader {
    private static final Logger logger = LogManager.getLogger(DataReader.class);

    private static final String LINE_SPLIT_REGEX = "\\s+";

    private final String filePath;

    public DataReader(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Taxi> loadTaxis() {
        ArrayList<Taxi> taxis = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("TAXI")) {
                    String[] parts = line.split(LINE_SPLIT_REGEX);
                    int id = Integer.parseInt(parts[1]);
                    double x = Double.parseDouble(parts[2]);
                    double y = Double.parseDouble(parts[3]);
                    taxis.add(new Taxi(id, x, y));
                }
            }
        } catch (IOException e) {
            logger.error(AppConstant.ERROR_TASK_EXECUTION, e);
        }
        return taxis;
    }

    public ArrayList<Passenger> loadPassengers() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("PASSENGER")) {
                    String[] parts = line.split(LINE_SPLIT_REGEX);
                    String name = parts[1];
                    double x = Double.parseDouble(parts[2]);
                    double y = Double.parseDouble(parts[3]);
                    double dx = Double.parseDouble(parts[4]);
                    double dy = Double.parseDouble(parts[5]);
                    passengers.add(new Passenger(name, x, y, dx, dy));
                }
            }
        } catch (IOException e) {
            logger.error(AppConstant.ERROR_TASK_EXECUTION, e);
        }
        return passengers;
    }
}
