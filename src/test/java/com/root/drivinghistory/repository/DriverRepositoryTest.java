package com.root.drivinghistory.repository;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverRepositoryTest {

    DriverRepository repo = new DriverRepository();

    @Test
    public void wont_save_nulls() {
        repo.save(null);
        assertThat(repo.getAll()).isEmpty();
    }
}
