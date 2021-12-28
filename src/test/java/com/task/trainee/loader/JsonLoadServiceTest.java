package com.task.trainee.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.trainee.entities.Report;
import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.SensorStatus;
import com.task.trainee.entities.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JsonLoadServiceTest {

    @InjectMocks
    private JsonLoadService jsonLoadService;

    private Sensor sensor = new Sensor();
    private Sensor sensorFromJson = new Sensor();
    private Report report = new Report();
    private File file = new File("src/main/resources/test/test.json");
    private static final String TEST_REPORT_FILE_PATH = "src/main/resources/test/report.json";

    @BeforeEach
    private void sensorInitialization() {
        sensor.setId("1b5d3256");
        sensor.setName("S-Light");
        sensor.setDescription("Light sensor");
        sensor.setType("Light");
        sensor.setBatteryPercentage(5);
        sensor.setStatus(new SensorStatus(Status.OFFLINE, "night"));
        sensor.setModifiedTime(1623411833025L);
        sensor.setModifiedBy("admin@test.com");
        sensor.setCreatedTime(1623411829895L);
        sensor.setCreatedBy("admin@test.com");

        report.setNumberOfSensors(9);
        report.setNumberOfOnlineSensors(5);
        report.setNumberOfBrokenSensors(3);
    }

    @Test
    void loadSensorFromJsonFile_idParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals(sensor.getId(), sensorFromJson.getId());
        assertEquals("1b5d3256", sensorFromJson.getId());
    }

    @Test
    void loadSensorFromJsonFile_idParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertFalse(!sensorFromJson.getId().equals(sensor.getId()));
        assertNotEquals("16", sensorFromJson.getId());
    }

    @Test
    void loadSensorFromJsonFile_typeParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertTrue(sensorFromJson.getType().equals(sensor.getType()));
        assertEquals("Light", sensorFromJson.getType());
    }

    @Test
    void loadSensorFromJsonFile_typeParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertTrue(!sensorFromJson.getType().equals(sensor.getType()));
        assertEquals("WRONG", sensorFromJson.getType());
    }

    @Test
    void loadSensorFromJsonFile_descriptionParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals(sensor.getDescription(), sensorFromJson.getDescription());
        assertEquals("Light sensor", sensorFromJson.getDescription());
    }

    @Test
    void loadSensorFromJsonFile_descriptionParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertFalse(!sensorFromJson.getDescription().equals(sensor.getDescription()));
        assertNotEquals("WRONG", sensorFromJson.getDescription());
    }

    @Test
    void loadSensorFromJsonFile_nameParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals(sensor.getName(), sensorFromJson.getName());
        assertEquals("S-Light", sensorFromJson.getName());
    }

    @Test
    void loadSensorFromJsonFile_nameParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertFalse(sensorFromJson.getName().equals(sensor.getName()));
        assertNotEquals("WRONG", sensorFromJson.getName());
    }

    @Test
    void loadSensorFromJsonFile_batteryPercentageParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals(sensor.getBatteryPercentage(), sensorFromJson.getBatteryPercentage());
        assertTrue(sensorFromJson.getBatteryPercentage() == 5);
    }

    @Test
    void loadSensorFromJsonFile_batteryPercentageParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertFalse(sensorFromJson.getBatteryPercentage() == sensor.getBatteryPercentage());
        assertFalse(sensorFromJson.getBatteryPercentage() == 100);
    }

    @Test
    void loadSensorFromJsonFile_sensorStatusParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertTrue(sensorFromJson.getStatus().getStatus().equals(Status.OFFLINE));
        assertEquals(sensor.getStatus().getStatus(), sensorFromJson.getStatus().getStatus());
    }

    @Test
    void loadSensorFromJsonFile_sensorStatusParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertNotEquals(Status.ONLINE, sensorFromJson.getStatus().getStatus());
        assertFalse(!sensorFromJson.getStatus().getStatus().equals(sensor.getStatus().getStatus()));
    }

    @Test
    void loadSensorFromJsonFile_sensorStatusValueParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals("night", sensorFromJson.getStatus().getValue());
        assertEquals(sensor.getStatus().getValue(), sensorFromJson.getStatus().getValue());
    }

    @Test
    void loadSensorFromJsonFile_sensorStatusValueParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertNotEquals("day", sensorFromJson.getStatus().getValue());
        assertFalse(sensorFromJson.getStatus().getValue().equals(sensor.getStatus().getValue()));
    }

    @Test
    void loadSensorFromJsonFile_modifiedByParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals("admin@test.com", sensorFromJson.getModifiedBy());
        assertTrue(sensorFromJson.getModifiedBy().equals(sensor.getModifiedBy()));
    }

    @Test
    void loadSensorFromJsonFile_modifiedByParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertNotEquals("WRONG@test.com", sensorFromJson.getModifiedBy());
        assertFalse(!sensorFromJson.getModifiedBy().equals(sensor.getModifiedBy()));
    }

    @Test
    void loadSensorFromJsonFile_modifiedTimeParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals(1623411833025L, sensorFromJson.getModifiedTime());
        assertTrue(sensorFromJson.getModifiedTime() == sensor.getModifiedTime());
    }

    @Test
    void loadSensorFromJsonFile_modifiedTimeParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertNotEquals(555555555999L, sensorFromJson.getModifiedTime());
        assertNotEquals(sensor.getModifiedTime() + 23L, sensorFromJson.getModifiedTime());
    }

    @Test
    void loadSensorFromJsonFile_createdByParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals("admin@test.com", sensorFromJson.getCreatedBy());
        assertTrue(sensorFromJson.getCreatedBy().equals(sensor.getCreatedBy()));
    }

    @Test
    void loadSensorFromJsonFile_createdByParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertNotEquals("WRONG@test.com", sensorFromJson.getCreatedBy());
        assertFalse(!sensorFromJson.getCreatedBy().equals(sensor.getCreatedBy()));
    }

    @Test
    void loadSensorFromJsonFile_createdTimeParameter_successCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertEquals(1623411829895L, sensorFromJson.getCreatedTime());
        assertTrue(sensorFromJson.getCreatedTime() == sensor.getCreatedTime());
    }

    @Test
    void loadSensorFromJsonFile_createdTimeParameter_failCase() {
        sensorFromJson = jsonLoadService.loadSensorFromJSONFile(file);

        assertNotEquals(555555555999L, sensorFromJson.getCreatedTime());
        assertNotEquals(sensor.getCreatedTime() + 2L, sensorFromJson.getCreatedTime());
    }

    @Test
    void loadReportToJsonFile_correctParametersCase() throws IOException {
        jsonLoadService.loadReportToJSONFile(report, TEST_REPORT_FILE_PATH);
        String jsonString = new String(Files.readAllBytes(Paths.get(TEST_REPORT_FILE_PATH)));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Report testReport = gson.fromJson(jsonString, Report.class);

        assertTrue(report.equals(testReport));
        assertTrue(report.getNumberOfBrokenSensors() == testReport.getNumberOfBrokenSensors());
        assertEquals(report.getNumberOfOnlineSensors(), testReport.getNumberOfOnlineSensors());
        assertEquals(report.getNumberOfSensors(), testReport.getNumberOfSensors());
    }

    @Test
    void loadReportToJsonFile_incorrectParametersCase() throws IOException {
        jsonLoadService.loadReportToJSONFile(report, TEST_REPORT_FILE_PATH);
        String jsonString = new String(Files.readAllBytes(Paths.get(TEST_REPORT_FILE_PATH)));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Report testReport = gson.fromJson(jsonString, Report.class);

        assertFalse(!report.equals(testReport));
        assertFalse(report.getNumberOfBrokenSensors() != testReport.getNumberOfBrokenSensors());
        assertNotEquals(100000, testReport.getNumberOfOnlineSensors());
        assertNotEquals(200000, testReport.getNumberOfSensors());
    }
}
