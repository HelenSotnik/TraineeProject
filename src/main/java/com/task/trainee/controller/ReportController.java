package com.task.trainee.controller;

import com.task.trainee.entities.Report;
import com.task.trainee.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Report findReport() {
        return service.getReport();
    }
}
