package com.root.drivinghistory;

import lombok.*;

@AllArgsConstructor
@Data
public class Trip {
    String driverName;
    Integer time;
    Double distance;
}
