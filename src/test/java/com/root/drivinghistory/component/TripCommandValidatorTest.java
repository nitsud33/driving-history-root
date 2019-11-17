package com.root.drivinghistory.component;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TripCommandValidatorTest {

    TripCommandValidator tripCommandValidator = new TripCommandValidator();

    @Test
    public void null_line_is_invalid() {
        assertThat(tripCommandValidator.isValidTripCommand(null)).isFalse();
    }

    @Test
    public void empty_line_is_invalid() {
        assertThat(tripCommandValidator.isValidTripCommand("")).isFalse();
    }

    @Test
    public void blank_line_is_invalid() {
        assertThat(tripCommandValidator.isValidTripCommand("    ")).isFalse();
    }

    @Test
    public void trip_command_must_start_with_trip() {
        assertThat(tripCommandValidator.isValidTripCommand("Add Trip")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("TTrip ")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Dan 02:15 07:45 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Donkey Kong 02:15 07:45 0")).isFalse();
    }

    @Test
    public void tripDataWithInvalidStartTimesIsInvalid() {
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 24:00 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 01:60 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan ab:00 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:ab 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan -1:00 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan .5:00 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:-1 23:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:.5 23:00 0")).isFalse();
    }

    @Test
    public void tripDataWithInvalidEndTimesIsInvalid() {
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 24:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 01:60 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:ab 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 ab:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:-1 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 -1:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 .5:00 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:.5 0")).isFalse();
    }

    @Test
    public void tripDataWithStartTimeThatsAfterTheEndTimeIsIgnored() {
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 03:00 02:59 0")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 03:01 03:00 0")).isFalse();
    }

    @Test
    public void tripDataWithInvalidMilesIsIgnored() {
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:59 Not a real milage")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:59 a")).isFalse();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:59 -1")).isFalse();
    }

    @Test
    public void trip_with_name_startTime_endTime_distance_is_valid() {
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:59 0")).isTrue();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:59 23:00 0")).isTrue();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:59 23:00 17.3")).isTrue();
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:59 23:00 .3")).isTrue();
    }

}
