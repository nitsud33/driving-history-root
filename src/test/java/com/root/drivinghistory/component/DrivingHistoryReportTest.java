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
    public void report_with_one_driver_10_miles_in_one_hour_is_10mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 10"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 10 miles @ 10 mph"
        );
    }

    @Test
    public void report_with_one_driver_with_no_trips() {
        List<String> lines = asList(
                "Driver Dan"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 0 miles"
        );
    }

    @Test
    public void report_works_even_if_trip_appears_before_driver() {
        List<String> lines = asList(
                "Trip Dan 00:00 01:00 10",
                "Driver Dan"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 10 miles @ 10 mph"
        );
    }

    @Test
    public void report_ignores_trips_less_than_5mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 4.99999",
                "Trip Dan 00:00 04:00 19.9999999"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 0 miles"
        );
    }

    @Test
    public void report_accepts_trips_at_5mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 5"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 5 miles @ 5 mph"
        );
    }

    @Test
    public void report_ignores_trips_greater_than_100mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 100.00001",
                "Trip Dan 00:00 05:00 500.00001"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 0 miles"
        );
    }

    @Test
    public void report_accepts_trips_at_100mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 100"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 100 miles @ 100 mph"
        );
    }

    @Test
    public void report_rounds_5_point_4999mph_down_to_5mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 5.4999"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 5 miles @ 5 mph"
        );
    }

    @Test
    public void report_rounds_5_point_5mph_up_to_6mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 5.5"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 6 miles @ 6 mph"
        );
    }

    @Test
    public void report_rounds_10_point_49999_miles_down_to_10_miles() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 10.49999"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 10 miles @ 10 mph"
        );
    }

    @Test
    public void report_rounds_10_point_5_miles_up_to_11_miles() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 10.5"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 11 miles @ 11 mph"
        );
    }

    @Test
    public void report_shows_100_miles_in_10_hours_as_10mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 03:33 13:33 100"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 100 miles @ 10 mph"
        );
    }

    @Test
    public void trip_of_5_miles_in_1_hour_and_95_miles_in_9_hours_is_100_miles_total_and_10mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 5",
                "Trip Dan 10:00 19:00 95"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 100 miles @ 10 mph"
        );
    }

    @Test
    public void trip_of_1_mile_in_1_min_is_60mph() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 00:01 1"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 1 miles @ 60 mph"
        );
    }

    @Test
    public void trip_of_10_point_4999_miles_and_10_point_4999_miles_rounds_to_21_miles() {
        List<String> lines = asList(
                "Driver Dan",
                "Trip Dan 00:00 01:00 10.4999",
                "Trip Dan 00:00 01:00 10.4999"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Dan: 21 miles @ 10 mph"
        );
    }

    @Test
    public void report_sorts_50miles_above_49miles_above_48miles() {
        List<String> lines = asList(
                "Driver DonkeyKong",
                "Driver DiddyKong",
                "Driver DryDryBones",
                "Driver Mario",
                "Trip DiddyKong 00:00 01:00 48",
                "Trip DonkeyKong 00:00 05:00 50",
                "Trip Mario 10:00 16:00 49"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "DonkeyKong: 50 miles @ 10 mph",
                "Mario: 49 miles @ 8 mph",
                "DiddyKong: 48 miles @ 48 mph",
                "DryDryBones: 0 miles"
        );
    }

    @Test
    public void test_that_my_product_owner_wanted_me_to_run() {
        List<String> lines = asList(
                "Driver Dan",
                "Driver Lauren",
                "Driver Kumi",
                "Trip Dan 07:15 07:45 17.3",
                "Trip Dan 06:12 06:32 21.8",
                "Trip Lauren 12:01 13:16 42.0"
        );

        drivingHistoryReport.parse(lines);
        assertThat(drivingHistoryReport.report()).containsExactly(
                "Lauren: 42 miles @ 34 mph",
                "Dan: 39 miles @ 47 mph",
                "Kumi: 0 miles"
        );
    }
}
