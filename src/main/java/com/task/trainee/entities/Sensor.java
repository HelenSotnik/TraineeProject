package com.task.trainee.entities;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = " Sensor response model ", description = "Contains information about sensors")
public class Sensor {

    @NotBlank
    @ApiModelProperty(name = "Sensor id", example = "1p7b2380", required = true)
    private String id;

    @NotBlank
    @ApiModelProperty(name = "Name of sensor", example = "Window", required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(name = "Sensors description", required = true)
    private String description;

    @NotBlank
    @ApiModelProperty(name = "Sensor type", example = "Door", required = true)
    private String type;

    @Min(value = 0, message = "Minimum value of battery percentage is 0")
    @Max(value = 100, message = "Maximum value of battery percentage is 100")
    @SerializedName("battery_percentage")
    @ApiModelProperty(value = "Sensors battery percentage", example = "100", required = true)
    private int batteryPercentage;

    @NotNull
    @SerializedName("sensor_status")
    @ApiModelProperty(name = "Sensor status", required = true)
    private SensorStatus status;


    @SerializedName("modified_time")
    @ApiModelProperty(name = "Time when sensor was modified", example = "255516181", required = true)
    private Long modifiedTime;

    @NotBlank
    @SerializedName("modified_by")
    @ApiModelProperty(name = "Email by whom sensor was modified", example = "test@test.com", required = true)
    private String modifiedBy;


    @SerializedName("created_time")
    @ApiModelProperty(name = "Time when sensor was created", example = "255516181", required = true)
    private Long createdTime;

    @NotBlank
    @SerializedName("created_by")
    @ApiModelProperty(name = "Email by whom sensor was created", example = "test@test.com", required = true)
    private String createdBy;
}
