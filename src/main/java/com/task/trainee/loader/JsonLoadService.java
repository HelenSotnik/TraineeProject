package com.task.trainee.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.trainee.entities.Report;
import com.task.trainee.entities.Sensor;
import com.task.trainee.loader.util.LogJsonLoadService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;

@Component
public class JsonLoadService {

    private static final Logger LOG = Logger.getLogger(JsonLoadService.class);
    private static final String LOAD_SENSOR_FROM_JSON_FILE_METHOD_NAME = "loadSensorFromJSONFile(String path)";
    private static final String LOAD_REPORT_TO_JSON_FILE_METHOD_NAME = "loadReportToJSONFile(Report report, String reportFile)";

    public void loadReportToJSONFile(Report report, String reportFile) {
        try {
            File file = new File(reportFile);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, report);

            LOG.info(LogJsonLoadService.getInfoMessage(LOAD_REPORT_TO_JSON_FILE_METHOD_NAME));

        } catch (IOException e) {
            LOG.error(LogJsonLoadService.getIOExceptionMessage(LOAD_REPORT_TO_JSON_FILE_METHOD_NAME));
            e.printStackTrace();
        }
    }

    public Sensor loadSensorFromJSONFile(File file) {
        try {
            String jsonString = new String(Files.readAllBytes(file.toPath()));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.disableInnerClassSerialization().create();
            Sensor sensor = gson.fromJson(jsonString, Sensor.class);

            LOG.info(LogJsonLoadService.getInfoMessage(LOAD_SENSOR_FROM_JSON_FILE_METHOD_NAME));
            return sensor;

        } catch (IOException ex) {
            LOG.error(LogJsonLoadService.getIOExceptionMessage(LOAD_SENSOR_FROM_JSON_FILE_METHOD_NAME));
            ex.printStackTrace();
        }
        return null;
    }
}
