package com.root.drivinghistory.repository;

import com.root.drivinghistory.*;

import java.util.*;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class TripRepository {

    List<Trip> trips = new ArrayList<>();

    public void save(Trip trip){
        if (isValidTrip(trip)){
            trips.add(trip);
        }
    }

    public List<Trip> getAll(){
        return trips;
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
