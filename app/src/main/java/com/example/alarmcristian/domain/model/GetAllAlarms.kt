package com.example.alarmcristian.domain.model

import com.example.alarmcristian.data.AlarmRepository
import javax.inject.Inject

class GetAllAlarms @Inject constructor(private val repository: AlarmRepository) {
    suspend operator fun invoke(): List<Alarm> {
        val alarms = repository.getAllAlarmsFromDatabase()
        return alarms
    }
}