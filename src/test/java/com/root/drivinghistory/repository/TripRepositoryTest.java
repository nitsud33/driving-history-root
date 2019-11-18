package com.root.drivinghistory.repository;

import com.root.drivinghistory.*;
import org.junit.jupiter.api.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;
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

    @Test
    public void wont_save_trips_with_missing_or_negative_time() {
        repo.save(new Trip());
        repo.save(new Trip("Donkey Kong", null, 1.0));
        repo.save(new Trip("Donkey Kong", -1, 1.0));
        assertThat(repo.getAll()).isEmpty();
    }

    @Test
    public void wont_save_trips_with_missing_or_negative_distance() {
        repo.save(new Trip());
        repo.save(new Trip("Donkey Kong", 1, null));
        repo.save(new Trip("Donkey Kong", 1, -1.0));
        assertThat(repo.getAll()).isEmpty();
    }

    @Test
    public void will_save_single_trip() {
        Trip trip = new Trip(randomAlphanumeric(8), nextInt(), nextDouble());
        repo.save(trip);
        assertThat(repo.getAll()).containsExactly(trip);
    }

    @Test
    public void will_save_multiple_trips() {
        Trip trip1 = new Trip(randomAlphanumeric(8), nextInt(), nextDouble());
        Trip trip2 = new Trip(randomAlphanumeric(8), nextInt(), nextDouble());
        repo.save(trip1);
        repo.save(trip2);
        assertThat(repo.getAll()).containsExactly(trip1, trip2);
    }

    @Test
    public void will_save_duplicate_trips() {
        Trip trip1 = new Trip(randomAlphanumeric(8), nextInt(), nextDouble());
        repo.save(trip1);
        repo.save(trip1);
        assertThat(repo.getAll()).containsExactly(trip1, trip1);
    }

}
