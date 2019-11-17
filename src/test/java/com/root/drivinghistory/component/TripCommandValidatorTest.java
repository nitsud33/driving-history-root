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
    }

    @Test
    public void trip_with_name_startTime_endTime_distance_is_valid() {
        assertThat(tripCommandValidator.isValidTripCommand("Trip Dan 00:00 23:59 0")).isTrue();
    }

}
