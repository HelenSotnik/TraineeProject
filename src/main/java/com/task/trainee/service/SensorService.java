package com.task.trainee.service;

import com.task.trainee.entities.Status;
import com.task.trainee.loader.JsonLoadService;
import com.task.trainee.service.util.LogServiceMessageUtil;
import com.task.trainee.validator.Validator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.task.trainee.entities.Sensor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SensorService {

    private static final String SENSORS_PATH = "src/main/resources/sensors";

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
        List<Sensor> result = new ArrayList<>();

        try {
            List<File> filesList = Files.walk(Paths.get(SENSORS_PATH))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            for (File file : filesList) {
                if (file.isFile() && file.getPath().endsWith(".json")) {
                    Sensor sensor = loadService.loadSensorFromJSONFile(file);
                    result.add(sensor);
                }
            }
            LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_INVALIDATED_SENSORS_LIST_METHOD_NAME));
            return result;

        } catch (IOException ioException) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_INVALIDATED_SENSORS_LIST_METHOD_NAME));
            ioException.printStackTrace();
        }
        return null;
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

        List<Sensor> result = getValidatedListOfSensors(getInvalidatedListOfSensors()).stream()
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
        List<Sensor> result = getValidatedListOfSensors(getInvalidatedListOfSensors()).stream()
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .collect(Collectors.toList());

        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_METHOD_NAME));
        return result;
    }

    public List<Sensor> findAllSensorsByStatusAndType(Status status, String type) {
        List<Sensor> result = getValidatedListOfSensors(getInvalidatedListOfSensors()).stream()
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
        List<Sensor> result = getValidatedListOfSensors(getInvalidatedListOfSensors()).stream()
                .filter(sensor -> sensor.getStatus().getStatus().equals(status))
                .filter(sensor -> sensor.getBatteryPercentage() >= batteryPercentage)
                .collect(Collectors.toList());

        if (result == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_AND_BATTERY_METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, GET_ALL_SENSORS_BY_STATUS_AND_BATTERY_METHOD_NAME));
        return result;
    }
}
