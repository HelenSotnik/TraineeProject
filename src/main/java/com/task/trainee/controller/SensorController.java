package com.task.trainee.controller;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.Status;
import com.task.trainee.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping("/status/{status}/type/{type}/battery/{batteryPercentage}")
    @ResponseStatus(HttpStatus.OK)
    public List<Sensor> findAllSensorsByStatusTypeAndBatteryPercentage
            (@PathVariable("status") Status status,
             @PathVariable(value = "type", required = false) String type,
             @PathVariable(value = "batteryPercentage", required = false) Integer batteryPercentage) {
        return sensorService.findAllSensorsByStatusTypeBatteryPercentage(status, type, batteryPercentage);
    }

    @GetMapping("/status/{status}/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<Sensor> findAllSensorsByStatusAndType
            (@PathVariable("status") Status status,
             @PathVariable(value = "type", required = false) String type) {
        return sensorService.findAllSensorsByStatusAndType(status, type);
    }

    @GetMapping("/status/{status}/battery/{batteryPercentage}")
    @ResponseStatus(HttpStatus.OK)
    public List<Sensor> findAllSensorsByStatusAndBatteryPercentage
            (@PathVariable("status") Status status,
             @PathVariable(value = "batteryPercentage", required = false) Integer batteryPercentage) {
        return sensorService.findAllByStatusAndBatteryPercentage(status, batteryPercentage);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<Sensor> findAllSensorsByStatus(@PathVariable("status") Status status) {
        return sensorService.findAllSensorsByStatus(status);
    }
}
