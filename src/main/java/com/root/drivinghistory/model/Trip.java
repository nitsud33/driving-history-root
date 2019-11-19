package com.root.drivinghistory.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trip {
    String driverName;
    Integer time;
    Double distance;

    public Double getAverageSpeed(){
        if (distance.equals(0.0) || time.equals(0)){
            return 0.0;
        }
        return (distance * 60) / time;
    }
}
