package com.task.trainee.service;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.SensorStatus;
import com.task.trainee.entities.Status;
import com.task.trainee.mapper.JsonMapper;
import com.task.trainee.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @InjectMocks
    private SensorService sensorService;

    @Mock
    private JsonMapper jsonMapper;

    @Mock
    private Validator validator;

    private Sensor sensor = new Sensor();
    private List<Sensor> testList = new ArrayList<>();

    @BeforeEach
    private void validSensorInitialization() {
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

        when(jsonMapper.mapJSONToSensor(any())).thenReturn(sensor);
        when(validator.validate(anyList())).thenReturn(Collections.singletonList(sensor));
    }

    @Test
    void getInvalidatedListOfSensorsCheckBySizeCorrectValuesTest() {
        testList.add(jsonMapper.mapJSONToSensor(anyString()));
        assertSame(1, testList.size());
    }

    @Test
    void getInvalidatedListOfSensorsIncorrectValuesTest() {
        testList.add(jsonMapper.mapJSONToSensor(anyString()));
        assertNotSame(3, testList.size());
    }

    @Test
    void getValidatedListOfSensorsCorrectValuesTest() {
        assertEquals(1, validator.validate(anyList()).size());
    }

    @Test
    void getValidatedListOfSensorsIncorrectValuesTest() {
        assertNotSame(6, validator.validate(anyList()).size());
    }

    @Test
    void findAllSensorsByStatusTypeBatteryPercentageCorrectValuesTest() {
        List<Sensor> actualList =
                sensorService.findAllSensorsByStatusTypeBatteryPercentage(Status.Online, "Door", 50);
        assertTrue(actualList.get(0).getBatteryPercentage() > 50);
        assertEquals(Status.Online, actualList.get(0).getStatus().getStatus());
        assertSame("Door", actualList.get(0).getType());
    }

    @Test
    void findAllSensorsByStatusTypeBatteryPercentageIncorrectValuesTest() {
        List<Sensor> actualList =
                sensorService.findAllSensorsByStatusTypeBatteryPercentage(Status.Online, "Door", 50);
        assertFalse(actualList.get(0).getBatteryPercentage() == 10);
        assertNotEquals(Status.Offline, actualList.get(0).getStatus().getStatus());
        assertNotSame("Light", actualList.get(0).getType());
    }


    @Test
    void findAllSensorsByStatusCorrectValueTest() {
        List<Sensor> actualList =
                sensorService.findAllSensorsByStatus(Status.Offline);
        assertSame(Status.Offline, actualList.get(0).getStatus().getStatus());
    }

    @Test
    void findAllSensorsByStatusInCorrectValueTest() {
        List<Sensor> actualList =
                sensorService.findAllSensorsByStatus(Status.Offline);
        assertFalse(actualList.get(0).getStatus().getStatus().equals(Status.Online));
    }

    @Test
    void findAllSensorsByStatusAndTypeCorrectValuesTest() {
        List<Sensor> actualList =
                sensorService.findAllSensorsByStatusAndType(Status.Online, "Window");
        assertEquals(Status.Online, actualList.get(0).getStatus().getStatus());
        assertTrue(actualList.get(0).getType().equals("Window"));
    }

    @Test
    void findAllSensorsByStatusAndTypeIncorrectValuesTest() {
        List<Sensor> actualList =
                sensorService.findAllSensorsByStatusAndType(Status.Online, "Window");
        assertNotSame(Status.Offline, actualList.get(0).getStatus().getStatus());
        assertFalse(actualList.get(0).getType().equals("Door"));
    }

    @Test
    void findAllByStatusAndBatteryPercentageCorrectValues() {
        List<Sensor> actualList =
                sensorService.findAllByStatusAndBatteryPercentage(Status.Online, 10);
        assertTrue(actualList.get(0).getStatus().getStatus().equals(Status.Online));
        assertFalse(actualList.get(0).getBatteryPercentage() > 5);
    }

    @Test
    void findAllByStatusAndBatteryPercentageIncorrectValues() {
        List<Sensor> actualList =
                sensorService.findAllByStatusAndBatteryPercentage(Status.Online, 10);
        assertFalse(actualList.get(0).getStatus().getStatus().equals(Status.Offline));
        assertEquals(5, actualList.get(0).getBatteryPercentage());
    }
}
