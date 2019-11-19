package com.root.drivinghistory.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trip {
    String driverName;
    Integer time;
    Double distance;
}
