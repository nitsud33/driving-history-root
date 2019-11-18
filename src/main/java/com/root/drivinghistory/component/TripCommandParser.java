package com.root.drivinghistory.component;

import com.root.drivinghistory.*;
import com.root.drivinghistory.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static java.lang.Integer.valueOf;

@Service
public class TripCommandParser {

    TripRepository repo;

    public TripCommandParser(@Autowired TripRepository repo) {
        this.repo = repo;
    }

    public void parseTripCommand(String line) {
        if (line == null) {
            return;
        }
        String[] words = line.split("[ :]");
        if (words.length > 6 &&
                words[0].equals("Trip")
        ) {
            try {
                Integer startTimeHour = valueOf(words[2]);
                Integer startTimeMinute = valueOf(words[3]);
                Integer endTimeHour = valueOf(words[4]);
                Integer endTimeMinute = valueOf(words[5]);
                Double miles = Double.valueOf(words[6]);
                if (
                        startTimeHour > 23 ||
                                startTimeHour < 0 ||
                                startTimeMinute > 59 ||
                                startTimeMinute < 0 ||
                                endTimeHour > 23 ||
                                endTimeHour < 0 ||
                                endTimeMinute > 59 ||
                                endTimeMinute < 0 ||
                                endTimeHour < startTimeHour ||
                                endTimeHour.equals(startTimeHour) && endTimeMinute < startTimeMinute ||
                                miles < 0
                ) {
                    return;
                }
                Integer time = 60 * (endTimeHour - startTimeHour) + endTimeMinute - startTimeMinute;
                repo.save(new Trip(words[1], time, miles));
            } catch (NumberFormatException nfe) {
            }
        }
    }
}
