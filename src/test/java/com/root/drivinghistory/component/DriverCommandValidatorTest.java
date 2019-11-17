package com.root.drivinghistory.component;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverCommandValidatorTest {

    DriverCommandValidator driverCommandValidator = new DriverCommandValidator();

    @Test
    public void null_line_is_invalid () {
        assertThat(driverCommandValidator.isValidDriverCommandLine(null)).isFalse();
    }
}
