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
    public void empty_line_is_invalid () {
        assertThat(tripCommandValidator.isValidTripCommand("")).isFalse();
    }

    @Test
    public void blank_line_is_invalid () {
        assertThat(tripCommandValidator.isValidTripCommand("    ")).isFalse();
    }
}
