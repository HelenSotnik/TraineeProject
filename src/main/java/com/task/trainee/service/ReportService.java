package com.task.trainee.service;

import com.task.trainee.entities.Report;
import com.task.trainee.entities.Status;
import com.task.trainee.mapper.JsonMapper;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final SensorService sensorService;
    private final JsonMapper jsonMapper;

    public ReportService(SensorService sensorService, JsonMapper jsonMapper) {
        this.sensorService = sensorService;
        this.jsonMapper = jsonMapper;
    }

    public Report getReport() {
        int numberOfSensors = sensorService.getInvalidatedListOfSensors().size();
        int numberOfBrokenSensors = sensorService.getInvalidatedListOfSensors().size() -
                sensorService.getValidatedListOfSensors().size();
        int numberOfOnlineSensors = sensorService.findAllSensorsByStatus(Status.Online).size();

        Report report = new Report();
        report.setNumberOfSensors(numberOfSensors);
        report.setNumberOfBrokenSensors(numberOfBrokenSensors);
        report.setNumberOfOnlineSensors(numberOfOnlineSensors);
        jsonMapper.mapReportToJSON(report);
        return report;
    }
}
