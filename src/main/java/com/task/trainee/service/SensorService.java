package com.task.trainee.service;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.Status;
import com.task.trainee.mapper.JsonMapper;
import com.task.trainee.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SensorService {
    private final String DOOR_SENSOR_0a4b2386 = "src/main/resources/sensors/door_sensor_0a4b2386.json";
    private final String DOOR_SENSOR_1b9b2380 = "src/main/resources/sensors/door_sensor_1b9b2380.json";
    private final String DOOR_SENSOR_0p0p2380 = "src/main/resources/sensors/door_sensor_with_wrong_email_0p0p2380.json";
    private final String LITE_SENSOR_1b5d3256 = "src/main/resources/sensors/lite_sensor_1b5d3256.json";
    private final String LITE_SENSOR_3b6o4254 = "src/main/resources/sensors/lite_sensor_3b6o4254.json";
    private final String LITE_SENSOR_5y6o4254 = "src/main/resources/sensors/lite_sensor_5y6o4254.json";
    private final String WINDOW_SENSOR_8j9f3259 = "src/main/resources/sensors/window_sensor_8j9f3259.json";
    private final String WINDOW_SENSOR_2c5d3259 = "src/main/resources/sensors/window_sensor_with_wrong_fields_2c5d3259.json";
    private final String BROKEN_SENSOR_11111 = "src/main/resources/sensors/broken_sensor_11111.json";

    private final JsonMapper mapper;
    private final Validator validator;

    public SensorService(JsonMapper mapper, Validator validator) {
        this.mapper = mapper;
        this.validator = validator;
    }

    public List<Sensor> getInvalidatedListOfSensors() {
        Sensor ds1 = mapper.mapJSONToSensor(DOOR_SENSOR_0a4b2386);
        Sensor ds2 = mapper.mapJSONToSensor(DOOR_SENSOR_0p0p2380);
        Sensor ds3 = mapper.mapJSONToSensor(DOOR_SENSOR_1b9b2380);
        Sensor ls1 = mapper.mapJSONToSensor(LITE_SENSOR_1b5d3256);
        Sensor ls2 = mapper.mapJSONToSensor(LITE_SENSOR_3b6o4254);
        Sensor ls3 = mapper.mapJSONToSensor(LITE_SENSOR_5y6o4254);
        Sensor ws1 = mapper.mapJSONToSensor(WINDOW_SENSOR_2c5d3259);
        Sensor ws2 = mapper.mapJSONToSensor(WINDOW_SENSOR_8j9f3259);
        Sensor bs = mapper.mapJSONToSensor(BROKEN_SENSOR_11111);

        List<Sensor> result = new ArrayList<>();
        Collections.addAll(result, ds1, ds2, ds3, ls1, ls2, ls3, ws1, ws2, bs);
        return result;
    }

    public List<Sensor> getValidatedListOfSensors() {
        List<Sensor> invalidList = new ArrayList<>();
        invalidList.addAll(getInvalidatedListOfSensors());
        return validator.validate(invalidList);
    }

    public List<Sensor> findAllSensorsByStatusTypeBatteryPercentage(Status status, String type, Integer batteryPercentage) {
        return StreamSupport.stream(getValidatedListOfSensors().spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .filter(sensor -> sensor.getType().equals(type))
                .filter(sensor -> sensor.getBatteryPercentage() >= batteryPercentage)
                .collect(Collectors.toList());

    }

    public List<Sensor> findAllSensorsByStatus(Status status) {
        return StreamSupport.stream(getValidatedListOfSensors().spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .collect(Collectors.toList());

    }

    public List<Sensor> findAllSensorsByStatusAndType(Status status, String type) {
        return StreamSupport.stream(getValidatedListOfSensors().spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .filter(sensor -> sensor.getType().equals(type))
                .collect(Collectors.toList());

    }

    public List<Sensor> findAllByStatusAndBatteryPercentage(Status status, Integer batteryPercentage) {
        return StreamSupport.stream(getValidatedListOfSensors().spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .filter(sensor -> sensor.getBatteryPercentage() >= batteryPercentage)
                .collect(Collectors.toList());
    }
}
