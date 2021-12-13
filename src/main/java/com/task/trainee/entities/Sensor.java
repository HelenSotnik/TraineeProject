package com.task.trainee.entities;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    private String id;
    private String name;
    private String description;
    private String type;

    @SerializedName("battery_percentage")
    private int batteryPercentage;

    @SerializedName("sensor_status")
    private SensorStatus status;

    @SerializedName("modified_time")
    private Long modifiedTime;

    @SerializedName("modified_by")
    private String modifiedBy;

    @SerializedName("created_time")
    private Long createdTime;

    @SerializedName("created_by")
    private String createdBy;
}
