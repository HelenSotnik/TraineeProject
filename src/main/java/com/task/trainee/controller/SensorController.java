package com.task.trainee.controller;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.Status;
import com.task.trainee.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@Api(value = "Sensor controller")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @ApiOperation(value = "Get list of sensors by Status,Type,Min Battery Percentage")
    @GetMapping("/sensors")
    @ResponseBody
    public List<Sensor> findAllByStatusTypeAndBatteryPercentage(
            @RequestParam(name = "status") Status status,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "battery", required = false)
            @Min(value = 0, message = "Minimum value of battery percentage is 0")
            @Max(value = 100, message = "Maximum value of battery percentage is 100") Integer battery) {
        return sensorService.findAllSensors(status, type, battery);
    }
}
