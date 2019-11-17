package com.root.drivinghistory.component;

import com.root.drivinghistory.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static java.util.Arrays.asList;

@Service
public class DriverCommandParser {

    DriverRepository repo;

    public DriverCommandParser(@Autowired DriverRepository repo) {
        this.repo = repo;
    }

    public void parseAndSaveDriverName(String line) {
        if (isValidDriverCommandLine(line)) {
            List<String> words = asList(line.split("\\s+"));
            repo.save(
                    words
                            .subList(1, words.size())
                            .stream()
                            .reduce((a, b) -> a + " " + b)
                            .orElse(null)
            );
        }
    }

    private boolean isValidDriverCommandLine(String line) {
        if (line == null) {
            return false;
        }
        String[] words = line.split("\\s+");
        return words.length > 1 && words[0].equals("Driver");
    }
}
