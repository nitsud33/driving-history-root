package com.root.drivinghistory.component;

import com.root.drivinghistory.model.*;
import com.root.drivinghistory.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.Math.round;

@Service
public class DrivingHistoryReport {

    DriverCommandParser driverCommandParser;
    TripCommandParser tripCommandParser;
    DriverRepository driverRepository;
    TripRepository tripRepository;

    @Autowired
    public DrivingHistoryReport(
            DriverCommandParser driverCommandParser,
            TripCommandParser tripCommandParser,
            DriverRepository driverRepository,
            TripRepository tripRepository
    ) {
        this.driverCommandParser = driverCommandParser;
        this.tripCommandParser = tripCommandParser;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
    }

    public void parse(List<String> lines) {
        lines.forEach(this::parse);
    }

    public void parse(String line) {
        driverCommandParser.parseAndSaveDriverName(line);
        tripCommandParser.parseAndSaveTrip(line);
    }

    public List<String> report() {
        return driverRepository.getAll().stream()
                .map(aggregateDriverTrips)
                .sorted(totalDistanceDescending)
                .map(toDriverDistanceAndSpeedString)
                .collect(Collectors.toList());
    }

    private Predicate<Trip> isGreaterThan5PMHAndLessThan100MPH = (trip) ->
            trip.getAverageSpeed() <= 100.0 && trip.getAverageSpeed() >= 5.0;

    private BinaryOperator<Trip> combineTrips = (trip1, trip2) ->
            new Trip(trip1.getDriverName(), trip1.getTime() + trip2.getTime(), trip1.getDistance() + trip2.getDistance());

    private Comparator<Trip> totalDistanceDescending = (trip1, trip2) -> trip1.getDistance() > trip2.getDistance() ? -1 : trip1.getDistance().equals(trip2.getDistance()) ? 0 : 1;

    private Function<Trip, String> toDriverDistanceAndSpeedString = trip ->
            trip.getDriverName() + ": " +
                    round(trip.getDistance()) + " miles" +
                    (round(trip.getDistance()) > 0 ?
                            " @ " + round(trip.getAverageSpeed()) + " mph" : "");

    private Function<String, Trip> aggregateDriverTrips = driverName ->
            tripRepository.getAll(driverName).stream()
                    .filter(isGreaterThan5PMHAndLessThan100MPH)
                    .reduce(new Trip(driverName, 0, 0.0), combineTrips);

}
