package com.task.trainee.controller;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.SensorStatus;
import com.task.trainee.entities.Status;
import com.task.trainee.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorControllerTest {

    @InjectMocks
    private SensorController sensorController;

    @Mock
    private SensorService sensorService;

    private Sensor sensor = new Sensor();
    private List<Sensor> testList = new ArrayList<>();

    @BeforeEach
    private void sensorInitialization() {
        sensor.setId("1p7b2380");
        sensor.setName("Sensor-D");
        sensor.setDescription("Door-Sensor");
        sensor.setType("Door");
        sensor.setBatteryPercentage(89);
        sensor.setStatus(new SensorStatus(Status.Online, "open"));
        sensor.setModifiedTime(2345627281L);
        sensor.setModifiedBy("controller@test.com");
        sensor.setCreatedTime(2345627281L);
        sensor.setCreatedBy("controller@test.com");

        testList.add(sensor);

        when(sensorService.findAllSensors(ArgumentMatchers.any(), anyString(), anyInt())).thenReturn(testList);
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_allParams_correctValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "Door", 40);

        assertEquals(1, resultList.size());

        assertTrue(resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));

        assertTrue(testList.get(0).getBatteryPercentage() >= resultList.get(0).getBatteryPercentage());

        assertSame("Door", resultList.get(0).getType());
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_allParams_incorrectValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "Door", 40);

        assertNotEquals(5, resultList.size());

        assertFalse(!resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));

        assertFalse(testList.get(0).getBatteryPercentage() < resultList.get(0).getBatteryPercentage());

        assertNotSame("Window", resultList.get(0).getType());
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_withOnlyStatusParam_correctValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "", 0);

        assertEquals(1, resultList.size());

        assertTrue(resultList.size() == testList.size());

        assertTrue(resultList.get(0).getStatus().getStatus().equals(Status.Online));

        assertTrue(resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_withOnlyStatusParam_incorrectValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "", 0);

        assertNotEquals(0, resultList.size());

        assertFalse(!resultList.get(0).getStatus().getStatus().equals(Status.Online));

        assertFalse(!resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));

    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_withStatusAndTypeParams_correctValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "Door", 0);

        assertEquals(1, resultList.size());

        assertTrue(resultList.get(0).getStatus().getStatus().equals(Status.Online));

        assertTrue(resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));

        assertSame(testList.get(0).getType(), resultList.get(0).getType());
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_withStatusAndTypeParams_incorrectValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "Door", 0);

        assertNotEquals(testList.size() + 1, resultList.size());

        assertFalse(resultList.get(0).getStatus().getStatus().equals(Status.Offline));

        assertFalse(!resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));

        assertNotSame("Light", resultList.get(0).getType());
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_withStatusAndMinBatteryPercentageParams_correctValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "", 20);

        assertEquals(testList.size(), resultList.size());

        assertTrue(resultList.get(0).getStatus().getStatus().equals(Status.Online));

        assertEquals(resultList.get(0).getStatus().getStatus(), testList.get(0).getStatus().getStatus());

        assertTrue(testList.get(0).getBatteryPercentage() >= resultList.get(0).getBatteryPercentage());
    }

    @Test
    void findAllByStatusTypeAndBatteryPercentage_withStatusAndMinBatteryPercentageParams_incorrectValuesCaseTest() {
        List<Sensor> resultList =
                sensorController.findAllByStatusTypeAndBatteryPercentage(Status.Online, "", 20);

        assertFalse(testList.size() != resultList.size());

        assertNotEquals(Status.Offline, resultList.get(0).getStatus().getStatus());

        assertFalse(!resultList.get(0).getStatus().getStatus().equals(testList.get(0).getStatus().getStatus()));

        assertFalse(testList.get(0).getBatteryPercentage() < resultList.get(0).getBatteryPercentage());
    }
}
