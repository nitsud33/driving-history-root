package com.root.drivinghistory.repository;

import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
public class DriverRepository {

    Set<String> driverNames = new HashSet<>();

    public void save(String driverName) {
        if (isNotBlank(driverName)) {
            driverNames.add(driverName);
        }
    }

    public List<String> getAll() {
        return new ArrayList<>(driverNames);
    }
}
