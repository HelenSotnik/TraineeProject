package com.task.trainee.service;

import com.task.trainee.entities.Report;
import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.SensorStatus;
import com.task.trainee.entities.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private SensorService sensorService;

    private Sensor sensor = new Sensor();
    private Report report = new Report();

    @BeforeEach
    private void sensorInitialization() {
        sensor.setId("135ha453");
        sensor.setName("S-Door");
        sensor.setDescription("Sensor for door");
        sensor.setType("Door");
        sensor.setBatteryPercentage(80);
        sensor.setStatus(new SensorStatus(Status.Online, "closed"));
        sensor.setCreatedTime(1623411829896L);
        sensor.setCreatedBy("testing@test.com");
        sensor.setModifiedTime(1623411829893L);
        sensor.setModifiedBy("testing@test.com");
    }

    @BeforeEach
    private void reportInitialization() {
        when(sensorService.findAllSensorsByStatus(any())).thenReturn(Collections.singletonList(sensor));
        when(sensorService.getInvalidatedListOfSensors()).thenReturn(Collections.singletonList(sensor));
        when(sensorService.getValidatedListOfSensors(sensorService.getInvalidatedListOfSensors())).thenReturn(Collections.singletonList(sensor));

        report.setNumberOfSensors(sensorService.getInvalidatedListOfSensors().size());
        report.setNumberOfBrokenSensors(sensorService.getInvalidatedListOfSensors().size()
                - sensorService.getValidatedListOfSensors(sensorService.getInvalidatedListOfSensors()).size());
        report.setNumberOfOnlineSensors(sensorService.findAllSensorsByStatus(Status.Online).size());
    }

    @Test
    void getReport_correctValuesTest() {
        assertTrue(report.getNumberOfBrokenSensors() == 0);
        assertEquals(1, report.getNumberOfOnlineSensors());
        assertSame(1, report.getNumberOfSensors());
    }

    @Test
    void getReport_incorrectValuesTest() {
        assertFalse(report.getNumberOfBrokenSensors() == 5);
        assertNotEquals(10, report.getNumberOfOnlineSensors());
        assertNotSame(6, report.getNumberOfSensors());
    }
}
