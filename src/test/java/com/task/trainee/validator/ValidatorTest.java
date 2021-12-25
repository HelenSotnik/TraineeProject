package com.task.trainee.validator;

import com.task.trainee.entities.Sensor;
import com.task.trainee.entities.SensorStatus;
import com.task.trainee.entities.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    @InjectMocks
    private Validator validator;

    private List<Sensor> testList = new ArrayList<>();
    private Sensor sensor = new Sensor();
    private final String EMAIL_REGEX = "([a-zA-Z0-9\\.]{1,30})\\@([a-zA-Z0-9\\.]{1,30})[\\.]com";

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
    }

    @Test
    void validate_correctValuesTest() {
        List<Sensor> validatedList = validator.validate(testList);
        assertEquals(1, validator.validate(testList).size());

        assertTrue(validatedList.get(0).getBatteryPercentage() <= 100);
        assertTrue(validatedList.get(0).getBatteryPercentage() > 0);

        assertTrue(validatedList.get(0).getStatus().getStatus().equals(Status.Offline) ||
                validatedList.get(0).getStatus().getStatus().equals(Status.Online));

        assertTrue(validatedList.get(0).getModifiedBy().matches(EMAIL_REGEX));
        assertTrue(validatedList.get(0).getCreatedBy().matches(EMAIL_REGEX));

        assertTrue(validatedList.get(0).getModifiedTime() != 0);
        assertTrue(validatedList.get(0).getCreatedTime() != 0);
    }

    @Test
    void validate_incorrectValuesTest() {
        List<Sensor> validatedList = validator.validate(testList);
        assertNotEquals(0, validator.validate(testList).size());

        assertFalse(validatedList.get(0).getBatteryPercentage() > 100);
        assertFalse(validatedList.get(0).getBatteryPercentage() < 0);

        assertFalse(validatedList.get(0).getStatus().getStatus().equals(Status.Offline));

        assertFalse(!validatedList.get(0).getModifiedBy().matches(EMAIL_REGEX));
        assertFalse(!validatedList.get(0).getCreatedBy().matches(EMAIL_REGEX));

        assertFalse(validatedList.get(0).getModifiedTime() == 0);
        assertFalse(validatedList.get(0).getCreatedTime() == 0);
    }

    @Test
    void validate_invalidSensorParametersCaseTest() {
        sensor.setType("");
        sensor.setBatteryPercentage(200);

        testList.add(sensor);

        List<Sensor> validatedList = validator.validate(testList);

        assertTrue(validatedList.size() == 0);
        assertTrue(validatedList.isEmpty());
    }
}
