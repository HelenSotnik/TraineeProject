package com.task.trainee.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SensorStatus {
    private Status status;
    private String value;
}
