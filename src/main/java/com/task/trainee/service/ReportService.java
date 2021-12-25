package com.task.trainee.service;

import com.task.trainee.entities.Report;
import com.task.trainee.entities.Status;
import com.task.trainee.loader.JsonLoadService;
import com.task.trainee.service.util.LogServiceMessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private static final Logger LOG = Logger.getLogger(ReportService.class);
    private static final String METHOD_NAME = "getReport()";
    private static final String SERVICE_NAME = "REPORT SERVICE";

    private final static String REPORT_FILE = "src/main/resources/reports/report.json ";

    private final SensorService sensorService;
    private final JsonLoadService jsonLoadService;

    public ReportService(SensorService sensorService, JsonLoadService jsonLoadService) {
        this.sensorService = sensorService;
        this.jsonLoadService = jsonLoadService;
    }

    public Report generateReport() {
        int numberOfSensors = sensorService.getInvalidatedListOfSensors().size();
        int numberOfBrokenSensors = sensorService.getInvalidatedListOfSensors().size() -
                sensorService.getValidatedListOfSensors(sensorService.getInvalidatedListOfSensors()).size();
        int numberOfOnlineSensors = sensorService.findAllSensorsByStatus(Status.Online).size();

        Report report = new Report();
        report.setNumberOfSensors(numberOfSensors);
        report.setNumberOfBrokenSensors(numberOfBrokenSensors);
        report.setNumberOfOnlineSensors(numberOfOnlineSensors);
        jsonLoadService.loadReportToJSONFile(report, REPORT_FILE);
        return report;
    }

    public Report getReport() {
        Report lastReport = generateReport();
        if (lastReport == null) {
            LOG.debug(LogServiceMessageUtil.getFailDebugMessage(SERVICE_NAME, METHOD_NAME));
        }

        LOG.info(LogServiceMessageUtil.getSuccessInfoMessage(SERVICE_NAME, METHOD_NAME));
        return lastReport;
    }
}
