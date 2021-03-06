package com.task.trainee.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Report about sensors", description = "Report generated by the system in accordance to current value sensor")
public class Report {

    @ApiModelProperty(name = "Overall number of sensors")
    private Integer numberOfSensors;

    @ApiModelProperty(name = "Number represents amount of broken sensors or with wrong values")
    private Integer numberOfBrokenSensors;

    @ApiModelProperty(name = "Number represents amount of sensors with value of status: Online")
    private Integer numberOfOnlineSensors;
}
