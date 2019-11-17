package com.root.drivinghistory.component;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverCommandValidatorTest {

    DriverCommandValidator driverCommandValidator = new DriverCommandValidator();

    @Test
    public void null_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine(null)).isFalse();
    }

    @Test
    public void empty_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("")).isFalse();
    }

    @Test
    public void blank_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("    ")).isFalse();
    }

    @Test
    public void line_must_start_with_Driver () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Add Dan")).isFalse();
        assertThat(driverCommandValidator.isValidDriverCommandLine("Add Driver Dan ")).isFalse();
        assertThat(driverCommandValidator.isValidDriverCommandLine("DDriver Dan ")).isFalse();
    }

    @Test
    public void Driver_without_name_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver")).isFalse();
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver ")).isFalse();
    }

    @Test
    public void Driver_with_name_is_valid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver Dan")).isTrue();
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver Donkey Kong")).isTrue();
    }

    @Test
    public void validator_ignores_extra_spaces_in_between_words () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver    Donkey   Kong")).isTrue();
    }

}
