package com.task.trainee.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorStatus {
    @SerializedName("status")
    private Status status;

    @SerializedName("value")
    private String value;
}
