package com.root.drivinghistory.component;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverCommandValidatorTest {

    DriverCommandValidator driverCommandValidator = new DriverCommandValidator();

    @Test
    public void null_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine(null)).isFalse();
        assertThat(driverCommandValidator.getDriverName(null)).isNull();
    }

    @Test
    public void empty_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("")).isFalse();
        assertThat(driverCommandValidator.getDriverName("")).isNull();
    }

    @Test
    public void blank_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("    ")).isFalse();
        assertThat(driverCommandValidator.getDriverName("    ")).isNull();
    }

    @Test
    public void line_must_start_with_Driver () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Add Dan")).isFalse();
        assertThat(driverCommandValidator.isValidDriverCommandLine("Add Driver Dan ")).isFalse();
        assertThat(driverCommandValidator.isValidDriverCommandLine("DDriver Dan ")).isFalse();

        assertThat(driverCommandValidator.getDriverName("Add Dan")).isNull();
        assertThat(driverCommandValidator.getDriverName("Add Driver Dan ")).isNull();
        assertThat(driverCommandValidator.getDriverName("DDriver Dan ")).isNull();
    }

    @Test
    public void Driver_without_name_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver")).isFalse();
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver ")).isFalse();

        assertThat(driverCommandValidator.getDriverName("Driver")).isNull();
        assertThat(driverCommandValidator.getDriverName("Driver ")).isNull();
    }

    @Test
    public void Driver_with_name_is_valid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver Dan")).isTrue();
        assertThat(driverCommandValidator.getDriverName("Driver Dan")).isEqualTo("Dan");
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver Donkey Kong")).isTrue();
        assertThat(driverCommandValidator.getDriverName("Driver Donkey Kong")).isEqualTo("Donkey Kong");
    }

    @Test
    public void validator_ignores_extra_spaces_in_between_words () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver    Donkey   Kong")).isTrue();
        assertThat(driverCommandValidator.getDriverName("Driver    Donkey   Kong")).isEqualTo("Donkey Kong");
    }

    @Test
    public void driver_can_have_name_driver () {
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver Driver")).isTrue();
        assertThat(driverCommandValidator.getDriverName("Driver Driver")).isEqualTo("Driver");
        assertThat(driverCommandValidator.isValidDriverCommandLine("Driver Is Driver Donkey Kong")).isTrue();
        assertThat(driverCommandValidator.getDriverName("Driver Is Driver Donkey Kong")).isEqualTo("Is Driver Donkey Kong");
    }

}
