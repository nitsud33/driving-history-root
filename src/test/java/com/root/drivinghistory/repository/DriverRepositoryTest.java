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

    @Test
    public void wont_save_empty_string_or_blanks() {
        repo.save("");
        repo.save("   ");
        assertThat(repo.getAll()).isEmpty();
    }

    @Test
    public void will_save_single_name() {
        repo.save("Donkey Kong");
        assertThat(repo.getAll()).containsExactly("Donkey Kong");
    }

    @Test
    public void will_save_multiple_names() {
        repo.save("Donkey Kong");
        repo.save("Diddy Kong");
        repo.save("Mario");
        assertThat(repo.getAll())
                .containsExactlyInAnyOrder("Donkey Kong", "Diddy Kong", "Mario");
    }

    @Test
    public void save_name_is_idempotent() {
        repo.save("Donkey Kong");
        repo.save("Donkey Kong");
        repo.save("Donkey Kong");
        assertThat(repo.getAll()).containsExactly("Donkey Kong");
    }

}
