package com.task.trainee.service;

import com.task.trainee.entities.Status;
import com.task.trainee.loader.JsonLoadService;
import com.task.trainee.service.util.LogServiceMessageUtil;
import com.task.trainee.validator.Validator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.task.trainee.entities.Sensor;

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

    private static final Logger LOG = Logger.getLogger(SensorService.class);
    private static final String GET_INVALIDATED_SENSORS_LIST_METHOD_NAME = "getInvalidatedListOfSensors()";
    private static final String GET_VALIDATED_SENSORS_LIST_METHOD_NAME = "getValidatedListOfSensors()";
    private static final String GET_ALL_SENSORS_METHOD_NAME = "findAllSensors()";
    private static final String GET_ALL_SENSORS_BY_STATUS_METHOD_NAME = "findAllSensorsByStatus(Status status)";
    private static final String GET_ALL_SENSORS_BY_STATUS_AND_TYPE_METHOD_NAME = "findAllSensorsByStatusAndType(Status status, String type)";
    private static final String GET_ALL_SENSORS_BY_STATUS_AND_BATTERY_METHOD_NAME = "findAllByStatusAndBatteryPercentage(Status status, Integer batteryPercentage)";
    private static final String SERVICE_NAME = "SENSOR SERVICE";

    private final JsonLoadService loadService;
    private final Validator validator;

    public SensorService(JsonLoadService loadService, Validator validator) {
        this.loadService = loadService;
        this.validator = validator;
    }

    public List<Sensor> getInvalidatedListOfSensors() {
        Sensor ds1 = loadService.loadSensorFromJSONFile(DOOR_SENSOR_0a4b2386);
        Sensor ds2 = loadService.loadSensorFromJSONFile(DOOR_SENSOR_0p0p2380);
        Sensor ds3 = loadService.loadSensorFromJSONFile(DOOR_SENSOR_1b9b2380);
        Sensor ls1 = loadService.loadSensorFromJSONFile(LITE_SENSOR_1b5d3256);
        Sensor ls2 = loadService.loadSensorFromJSONFile(LITE_SENSOR_3b6o4254);
        Sensor ls3 = loadService.loadSensorFromJSONFile(LITE_SENSOR_5y6o4254);
        Sensor ws1 = loadService.loadSensorFromJSONFile(WINDOW_SENSOR_2c5d3259);
        Sensor ws2 = loadService.loadSensorFromJSONFile(WINDOW_SENSOR_8j9f3259);
        Sensor bs = loadService.loadSensorFromJSONFile(BROKEN_SENSOR_11111);

        List<Sensor> result = new ArrayList<>();
        Collections.addAll(result, ds1, ds2, ds3, ls1, ls2, ls3, ws1, ws2, bs);
        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_INVALIDATED_SENSORS_LIST_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_INVALIDATED_SENSORS_LIST_METHOD_NAME));
        return result;
    }

    public List<Sensor> getValidatedListOfSensors(List<Sensor> invalidatedList) {
        List<Sensor> validList = validator.validate(invalidatedList);
        if (validList == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_VALIDATED_SENSORS_LIST_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_VALIDATED_SENSORS_LIST_METHOD_NAME));
        return validList;
    }

    public List<Sensor> findAllSensors(Status status, String type, Integer batteryPercentage) {
        if (type == null && batteryPercentage == null) {
            return findAllSensorsByStatus(status);
        }
        if (type != null && batteryPercentage == null) {
            return findAllSensorsByStatusAndType(status, type);
        }
        if (type == null && batteryPercentage != null) {
            return findAllByStatusAndBatteryPercentage(status, batteryPercentage);
        }

        List<Sensor> result = StreamSupport.stream(getValidatedListOfSensors(getInvalidatedListOfSensors()).spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .filter(sensor -> sensor.getType().equals(type))
                .filter(sensor -> sensor.getBatteryPercentage() >= batteryPercentage)
                .collect(Collectors.toList());

        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_ALL_SENSORS_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_ALL_SENSORS_METHOD_NAME));
        return result;
    }

    public List<Sensor> findAllSensorsByStatus(Status status) {
        List<Sensor> result = StreamSupport.stream(getValidatedListOfSensors(getInvalidatedListOfSensors()).spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .collect(Collectors.toList());

        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_METHOD_NAME));
        return result;
    }

    public List<Sensor> findAllSensorsByStatusAndType(Status status, String type) {
        List<Sensor> result = StreamSupport.stream(getValidatedListOfSensors(getInvalidatedListOfSensors()).spliterator(), false)
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .filter(sensor -> sensor.getType().equals(type))
                .collect(Collectors.toList());

        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_AND_TYPE_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_AND_TYPE_METHOD_NAME));
        return result;
    }

    public List<Sensor> findAllByStatusAndBatteryPercentage(Status status, Integer batteryPercentage) {
        List<Sensor> result = StreamSupport.stream(getValidatedListOfSensors(getInvalidatedListOfSensors()).spliterator(), false)
                .filter(sensor -> sensor.getBatteryPercentage() >= batteryPercentage)
                .collect(Collectors.toList());

        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_AND_BATTERY_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_AND_BATTERY_METHOD_NAME));
        return result;
    }
}
