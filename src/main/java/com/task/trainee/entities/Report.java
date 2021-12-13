package com.task.trainee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private Integer numberOfSensors;
    private Integer numberOfBrokenSensors;
    private Integer numberOfOnlineSensors;
}
