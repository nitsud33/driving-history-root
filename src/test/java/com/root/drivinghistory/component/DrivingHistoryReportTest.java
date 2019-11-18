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

    @Test
    public void report_accepts_trips_at_5mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 5"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 5 miles @ 5 mph"
        );
    }

    @Test
    public void report_ignores_trips_greater_than_100mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 100.00001"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 0 miles"
        );
    }

    @Test
    public void report_accepts_trips_at_100mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 100"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 100 miles @ 100 mph"
        );
    }

    @Test
    public void report_rounds_5_point_4999mph_down_to_5mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 5.4999"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 5 miles @ 5 mph"
        );
    }

    @Test
    public void report_rounds_5_point_5mph_up_to_6mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 5.5"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 6 miles @ 6 mph"
        );
    }

    @Test
    public void report_rounds_10_point_49999_miles_down_to_10_miles(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 10.49999"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 10 miles @ 10 mph"
        );
    }

    @Test
    public void report_rounds_10_point_5_miles_up_to_11_miles(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 10.5"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 11 miles @ 11 mph"
        );
    }

    @Test
    public void report_shows_100_miles_in_10_hours_as_10mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 03:33 13:33 100"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 100 miles @ 10 mph"
        );
    }

    @Test
    public void trip_of_5_miles_in_1_hour_and_95_miles_in_9_hours_is_100_miles_total_and_10mph(){
        String[] lines = new String[]{
                "Driver Dan",
                "Trip Dan 00:00 01:00 5",
                "Trip Dan 10:00 19:00 95"
        };

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 100 miles @ 10 mph"
        );
    }

}
