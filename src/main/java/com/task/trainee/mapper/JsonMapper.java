package com.task.trainee.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.trainee.entities.Report;
import com.task.trainee.entities.Sensor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JsonMapper {
    private final static String reportFile = "src/main/resources/reports/report.json ";

    public void mapReportToJSON(Report report) {
        try {
            File file = new File(reportFile);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, report);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sensor mapJSONToSensor(String path) {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get(path)));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(jsonString, Sensor.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
