package com.root.drivinghistory.repository;

import com.root.drivinghistory.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TripRepositoryTest {

    TripRepository repo = new TripRepository();

    @Test
    public void wont_save_nulls() {
        repo.save(null);
        assertThat(repo.getAll()).isEmpty();
    }

    @Test
    public void wont_save_trips_missing_or_blank_driver_name() {
        repo.save(new Trip());
        repo.save(new Trip(null, 1, 1.0));
        repo.save(new Trip("", 1, 1.0));
        repo.save(new Trip("    ", 1, 1.0));
        assertThat(repo.getAll()).isEmpty();
    }


}
