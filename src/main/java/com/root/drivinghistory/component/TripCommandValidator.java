package com.root.drivinghistory.component;

import static java.lang.Integer.valueOf;

public class TripCommandValidator {

    public boolean isValidTripCommand(String line) {
        if (line == null) {
            return false;
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
                    return false;
                }
                return true;
            } catch (NumberFormatException nfe) {
            }
        }
        return false;
    }
}
