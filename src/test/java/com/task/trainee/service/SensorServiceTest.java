package com.task.trainee.service;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.SensorStatus;
import com.task.trainee.entities.Status;
import com.task.trainee.loader.JsonLoadService;
import com.task.trainee.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @InjectMocks
    private SensorService sensorService;

    @Mock
    private JsonLoadService jsonLoadService;

    @Mock
    private Validator validator;

    private Sensor sensor = new Sensor();
    private List<Sensor> testList = new ArrayList<>();

    @BeforeEach
    private void sensorInitialization() {
        sensor.setId("135ha453");
        sensor.setName("S-Door");
        sensor.setDescription("Sensor for door");
        sensor.setType("Door");
        sensor.setBatteryPercentage(80);
        sensor.setStatus(new SensorStatus(Status.ONLINE, "closed"));
        sensor.setCreatedTime(1623411829896L);
        sensor.setCreatedBy("testing@test.com");
        sensor.setModifiedTime(1623411829893L);
        sensor.setModifiedBy("testing@test.com");
    }

    @Test
    void getInvalidatedListOfSensorsCheckBySize_correctValuesTest() {
        when(jsonLoadService.loadSensorFromJSONFile(any())).thenReturn(sensor);

        testList.add(jsonLoadService.loadSensorFromJSONFile(any()));
        assertSame(1, testList.size());
    }

    @Test
    void getInvalidatedListOfSensors_incorrectValuesTest() {
        when(jsonLoadService.loadSensorFromJSONFile(any())).thenReturn(sensor);

        testList.add(jsonLoadService.loadSensorFromJSONFile(any()));
        assertNotSame(3, testList.size());
    }

    @Test
    void getValidatedListOfSensors_correctValuesTest() {
        when(sensorService.getValidatedListOfSensors(anyList()))
                .thenReturn(Collections.singletonList(sensor));

        assertEquals(1, sensorService.getValidatedListOfSensors(testList).size());
    }

    @Test
    void getValidatedListOfSensors_incorrectValuesTest() {
        when(sensorService.getValidatedListOfSensors(anyList()))
                .thenReturn(Collections.singletonList(sensor));

        assertNotSame(6, sensorService.getValidatedListOfSensors(testList).size());
    }

    @Test
    void findAllSensorsByStatusTypeBatteryPercentage_correctValuesTest() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList =
                sensorService.findAllSensors(Status.ONLINE, "Door", 20);

        assertTrue(actualList.get(0).getBatteryPercentage() > 20);
        assertEquals(Status.ONLINE, actualList.get(0).getStatus().getStatus());
        assertSame("Door", actualList.get(0).getType());
    }

    @Test
    void findAllSensorsByStatusTypeBatteryPercentage_incorrectValuesTest() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList =
                sensorService.findAllSensors(Status.ONLINE, "Door", 50);

        assertFalse(actualList.get(0).getBatteryPercentage() == 10);
        assertNotEquals(Status.OFFLINE, actualList.get(0).getStatus().getStatus());
        assertNotSame("Light", actualList.get(0).getType());
    }


    @Test
    void findAllSensorsByStatus_correctValueTest() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList = sensorService.findAllSensorsByStatus(Status.ONLINE);

        assertSame(Status.ONLINE, actualList.get(0).getStatus().getStatus());
    }

    @Test
    void findAllSensorsByStatus_incorrectValueTest() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList = sensorService.findAllSensorsByStatus(Status.ONLINE);

        assertFalse(actualList.get(0).getStatus().getStatus().equals(Status.OFFLINE));
    }

    @Test
    void findAllSensorsByStatusAndType_correctValuesTest() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList =
                sensorService.findAllSensorsByStatusAndType(Status.ONLINE, "Door");

        assertEquals(Status.ONLINE, actualList.get(0).getStatus().getStatus());
        assertTrue(actualList.get(0).getType().equals("Door"));
    }

    @Test
    void findAllSensorsByStatusAndType_incorrectValuesTest() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList =
                sensorService.findAllSensorsByStatusAndType(Status.ONLINE, "Door");

        assertNotSame(Status.OFFLINE, actualList.get(0).getStatus().getStatus());
        assertFalse(actualList.get(0).getType().equals("Window"));
    }

    @Test
    void findAllByStatusAndBatteryPercentage_correctValues() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList =
                sensorService.findAllByStatusAndBatteryPercentage(Status.ONLINE, 10);

        assertTrue(actualList.get(0).getStatus().getStatus().equals(Status.ONLINE));
        assertFalse(actualList.get(0).getBatteryPercentage() < 5);
    }

    @Test
    void findAllByStatusAndBatteryPercentage_incorrectValues() {
        when(sensorService.findAllSensors(any(Status.class), "", 0))
                .thenReturn(Collections.singletonList(sensor));

        List<Sensor> actualList =
                sensorService.findAllByStatusAndBatteryPercentage(Status.ONLINE, 10);

        assertFalse(actualList.get(0).getStatus().getStatus().equals(Status.OFFLINE));
        assertNotEquals(5, actualList.get(0).getBatteryPercentage());
    }
}
