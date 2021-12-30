package com.task.trainee.controller;

import com.task.trainee.entities.Report;
import com.task.trainee.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Report controller")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/report")
    @ApiOperation(value = "Get sensors report")
    public Report findReport() {
      return service.getReport();
    }
}
