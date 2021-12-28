package com.task.trainee.validator;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.Status;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Validator {
    public List<Sensor> validate(List<Sensor> list) {
        String emailRegEx = "([a-zA-Z0-9\\.]{1,30})\\@([a-zA-Z0-9\\.]{1,30})[\\.]com";

        return list.stream()
                .filter(sensor -> sensor.getBatteryPercentage() <= 100 && sensor.getBatteryPercentage() > 0)
                .filter(sensor -> sensor.getStatus().getStatus().equals(Status.OFFLINE) || sensor.getStatus().getStatus().equals(Status.ONLINE))
                .filter(sensor -> sensor.getModifiedTime() != 0)
                .filter(sensor -> sensor.getModifiedBy().matches(emailRegEx))
                .filter(sensor -> sensor.getCreatedTime() != 0)
                .filter(sensor -> sensor.getCreatedBy().matches(emailRegEx))
                .collect(Collectors.toList());
    }
}
