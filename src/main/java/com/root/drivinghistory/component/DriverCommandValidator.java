package com.root.drivinghistory.component;

import java.util.*;

import static java.util.Arrays.asList;

public class DriverCommandValidator {

    public String getDriverName(String line) {
        if (isValidDriverCommandLine(line)){
            List<String> words = asList(line.split("\\s+"));
            return words
                    .subList(1, words.size())
                    .stream()
                    .reduce((a,b) -> a + " " + b)
                    .orElse(null);
        }
        return null;
    }

    public boolean isValidDriverCommandLine(String line) {
        if (line == null) {
            return false;
        }
        String[] words = line.split("\\s+");
        return words.length > 1 && words[0].equals("Driver");
    }
}
