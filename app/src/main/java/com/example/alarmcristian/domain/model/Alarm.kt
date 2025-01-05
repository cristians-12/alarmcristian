package com.example.alarmcristian.domain.model

import com.example.alarmcristian.data.database.entities.AlarmEntity
import com.example.alarmcristian.data.model.AlarmModel
import java.util.Date

data class Alarm(val date: Date) {
}

fun AlarmModel.toDomain(): Alarm {
    return Alarm(date)
}

fun AlarmEntity.toDomain(): Alarm {
    return Alarm(date)
}