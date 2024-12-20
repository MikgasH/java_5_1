package org.example.taxiuber.utils;

public final class AppConstant {
    private AppConstant() {
    }

    public static final int THREAD_POOL_SIZE = 5;

    public static final double TAXI_SEARCH_RADIUS = 10.0;

    public static final String DATA_FILE_PATH = "src/main/resources/data.txt";

    public static final String NO_AVAILABLE_TAXIS = "No taxis available within radius {} km for {}";
    public static final String NO_SUITABLE_TAXI_FOUND = "No suitable taxi found among nearby taxis for {}";
    public static final String ORDER_ASSIGNED = "Order {}: Taxi {} assigned to passenger {}";
    public static final String ORDER_COMPLETED = "Order {}: Taxi {} finished ride for passenger {}";
    public static final String TAXIS_FOUND_IN_RADIUS = "Found {} taxis within {} km radius for passenger {}";

    public static final String ERROR_TASK_EXECUTION = "Error in worker: ";
}
