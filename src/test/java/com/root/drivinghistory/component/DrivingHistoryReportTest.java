package com.root.drivinghistory.component;

import com.root.drivinghistory.*;
import com.root.drivinghistory.repository.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;


public class DrivingHistoryReportTest {

    DriverRepository driverRepository = new DriverRepository();
    TripRepository tripRepository = new TripRepository();
    DriverCommandParser driverCommandParser = new DriverCommandParser(driverRepository);
    TripCommandParser tripCommandParser = new TripCommandParser(tripRepository);

    DrivingHistoryReport drivingHistoryReport = new DrivingHistoryReport(
            driverCommandParser,
            tripCommandParser,
            driverRepository,
            tripRepository
    );

    @Test
    public void reportOneDriver(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 10"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 10 miles @ 10 mph"
        );
    }

    @Test
    public void reportOneDriverWithNoTrips(){
        String[] lines = new String[]{
                "Driver Dan",
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 0 miles"
        );
    }

    @Test
    public void report_ignores_trips_less_than_5mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 4.99999"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 0 miles"
        );
    }
}
