package com.root.drivinghistory.component;

import com.root.drivinghistory.model.*;
import com.root.drivinghistory.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripCommandParserTest {

    @Mock
    TripRepository repo;

    @InjectMocks
    TripCommandParser tripCommandParser;

    @Test
    public void null_line_is_invalid() {
        tripCommandParser.parseAndSaveTrip(null);

        verifyNoInteractions(repo);
    }

    @Test
    public void empty_line_is_invalid() {
        tripCommandParser.parseAndSaveTrip("");

        verifyNoInteractions(repo);
    }

    @Test
    public void blank_line_is_invalid() {
        tripCommandParser.parseAndSaveTrip("    ");

        verifyNoInteractions(repo);
    }

    @Test
    public void trip_command_must_start_with_trip() {
        tripCommandParser.parseAndSaveTrip("Add Trip");
        tripCommandParser.parseAndSaveTrip("TTrip ");
        tripCommandParser.parseAndSaveTrip("Dan 02:15 07:45 0");
        tripCommandParser.parseAndSaveTrip("Donkey Kong 02:15 07:45 0");

        verifyNoInteractions(repo);
    }

    @Test
    public void trip_data_with_invalid_start_times_is_invalid() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 24:00 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 01:60 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan ab:00 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:ab 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan -1:00 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan .5:00 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:-1 23:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:.5 23:00 0");

        verifyNoInteractions(repo);
    }

    @Test
    public void trip_data_with_invalid_end_times_is_invalid() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 24:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 01:60 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:ab 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 ab:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:-1 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 -1:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 .5:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:.5 0");

        verifyNoInteractions(repo);
    }

    @Test
    public void trip_data_with_start_time_thats_after_the_end_time_is_ignored() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 03:00 02:59 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 03:01 03:00 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 03:00 03:00 0");

        verifyNoInteractions(repo);
    }

    @Test
    public void trip_data_with_invalid_miles_is_ignored() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:59 Not a real milage");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:59 a");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:59 -1");

        verifyNoInteractions(repo);
    }

    @Test
    public void one_hour_trip_is_saved_with_time_equals_60() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 01:00 0");

        verify(repo).save(new Trip("Dan", 60, 0.0));
    }

    @Test
    public void one_minute_trip_is_saved_with_time_equals_1() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 0");

        verify(repo).save(new Trip("Dan", 1, 0.0));
    }

    @Test
    public void one_minute_trip_with_different_start_hour_and_end_hour_saved_with_time_equals_1() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:59 01:00 0");

        verify(repo).save(new Trip("Dan", 1, 0.0));
    }

    @Test
    public void tripThatLastsEntireDayIsSaved() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 23:59 0");

        verify(repo).save(new Trip("Dan", 24 * 60 - 1, 0.0));
    }

    @Test
    public void will_save_milage_with_different_formats() {
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 10.0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 0.0");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 10.5");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 .3");
        tripCommandParser.parseAndSaveTrip("Trip Dan 00:00 00:01 0.5");

        verify(repo, times(2)).save(new Trip("Dan", 1, 0.0));
        verify(repo).save(new Trip("Dan", 1, 10.0));
        verify(repo).save(new Trip("Dan", 1, 10.5));
        verify(repo).save(new Trip("Dan", 1, 0.3));
        verify(repo).save(new Trip("Dan", 1, 0.5));
        verifyNoMoreInteractions(repo);
    }

}