package com.root.drivinghistory.component;

import com.root.drivinghistory.model.*;
import com.root.drivinghistory.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static java.lang.Integer.valueOf;

@Service
public class TripCommandParser {

    TripRepository repo;

    @Autowired
    public TripCommandParser(TripRepository repo) {
        this.repo = repo;
    }

    public void parseAndSaveTrip(String line) {
        if (isValidTrip(line)) {
            String[] words = line.split("[ :]");
            Integer startTimeHour = valueOf(words[2]);
            Integer startTimeMinute = valueOf(words[3]);
            Integer endTimeHour = valueOf(words[4]);
            Integer endTimeMinute = valueOf(words[5]);
            Double miles = Double.valueOf(words[6]);

            Integer time = 60 * (endTimeHour - startTimeHour) + endTimeMinute - startTimeMinute;
            repo.save(new Trip(words[1], time, miles));
        }

    }

    private boolean isValidTrip(String line) {
        if (line == null) {
            return false;
        }
        String[] words = line.split("[ :]");
        if (words.length > 6 &&
                words[0].equals("Trip")
        ) {
            try {
                if (startTimeAndEndTimesAreValid(valueOf(words[2]), valueOf(words[3]), valueOf(words[4]), valueOf(words[5])) && Double.valueOf(words[6]) >= 0) {
                    return true;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return false;
    }

    private boolean startTimeAndEndTimesAreValid(Integer startTimeHour, Integer startTimeMinute, Integer endTimeHour, Integer endTimeMinute) {
        return startTimeHour < 24 &&
                startTimeHour >= 0 &&
                startTimeMinute < 60 &&
                startTimeMinute >= 0 &&
                endTimeHour < 24 &&
                endTimeHour >= 0 &&
                endTimeMinute < 60 &&
                endTimeMinute >= 0 &&
                (endTimeHour > startTimeHour ||
                        endTimeHour.equals(startTimeHour) && endTimeMinute > startTimeMinute);
    }
}
