package com.root.drivinghistory.repository;

import com.root.drivinghistory.model.*;

import java.util.*;
import java.util.stream.*;

import static java.util.Arrays.asList;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class TripRepository {

    HashMap<String, List<Trip>> trips = new HashMap<>();

    public void save(Trip trip) {
        if (isValidTrip(trip)) {
            trips.compute(trip.getDriverName(),
                    (key, tripList) -> {
                        if (tripList == null) {
                            List<Trip> newTrips = new ArrayList<>();
                            newTrips.add(trip);
                            return newTrips;
                        } else {
                            tripList.add(trip);
                            return tripList;
                        }
                    });
        }
    }
    public List<Trip> getAll() {
        return trips.keySet()
                .stream()
                .flatMap(key -> trips.get(key).stream())
                .collect(Collectors.toList());
    }

    public List<Trip> getAll(String driverName) {
        return trips.getOrDefault(driverName, asList(new Trip(driverName, 0, 0.0)));
    }

    private boolean isValidTrip(Trip trip) {
        return trip != null &&
                isNotBlank(trip.getDriverName()) &&
                trip.getTime() != null &&
                trip.getTime() >= 0 &&
                trip.getDistance() != null &&
                trip.getDistance() >= 0.0;
    }
}
