package com.root.drivinghistory.component;

public class DriverCommandValidator {

    public boolean isValidDriverCommandLine(String line) {
        if (line == null) {
            return false;
        }
        String[] words = line.split(" ");
        return words.length > 1 && words[0].equals("Driver");
    }
}
