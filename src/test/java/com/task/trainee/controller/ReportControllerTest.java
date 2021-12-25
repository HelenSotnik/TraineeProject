package com.task.trainee.controller;

import com.task.trainee.entities.Report;
import com.task.trainee.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @InjectMocks
    private ReportController reportController;

    @Mock
    private ReportService reportService;

    private Report report = new Report();

    @BeforeEach
    private void reportInitialization() {
        report.setNumberOfSensors(10);
        report.setNumberOfBrokenSensors(2);
        report.setNumberOfOnlineSensors(5);

        when(reportService.getReport()).thenReturn(report);
    }

    @Test
    void findReport_correctValuesTest() {
        assertTrue(reportService.getReport().getNumberOfBrokenSensors() == report.getNumberOfBrokenSensors());
        assertTrue(reportService.getReport().getNumberOfBrokenSensors() == 2);

        assertEquals(report.getNumberOfOnlineSensors(), reportService.getReport().getNumberOfOnlineSensors());
        assertEquals(5, reportService.getReport().getNumberOfOnlineSensors());

        assertSame(report.getNumberOfSensors(), reportService.getReport().getNumberOfSensors());
        assertSame(10, reportService.getReport().getNumberOfSensors());
    }

    @Test
    void findReport_incorrectValuesTest() {
        assertFalse(reportService.getReport().getNumberOfBrokenSensors() != report.getNumberOfBrokenSensors());
        assertFalse(reportService.getReport().getNumberOfBrokenSensors() == 10);

        assertNotEquals(report.getNumberOfBrokenSensors(), reportService.getReport().getNumberOfOnlineSensors());
        assertNotEquals(10000, reportService.getReport().getNumberOfOnlineSensors());

        assertNotSame(report.getNumberOfBrokenSensors(), reportService.getReport().getNumberOfSensors());
        assertNotSame(0, reportService.getReport().getNumberOfSensors());
    }
}
