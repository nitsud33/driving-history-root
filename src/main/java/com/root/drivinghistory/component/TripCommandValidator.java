package com.root.drivinghistory.component;

public class TripCommandValidator {

    public boolean isValidTripCommand(String line) {
        if (line == null) {
            return false;
        }
        String[] words = line.split(" ");
        if (words.length > 0 && words[0].equals("Trip")){
            return true;
        }
        return false;
    }
}
