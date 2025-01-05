package com.example.alarmcristian.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AlarmModel(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: Date
) { }