package com.example.alarmcristian.data

import com.example.alarmcristian.data.database.dao.AlarmDao
import com.example.alarmcristian.data.database.entities.AlarmEntity
import com.example.alarmcristian.domain.model.Alarm
import com.example.alarmcristian.domain.model.toDomain
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {
    suspend fun getAllAlarmsFromDatabase(): List<Alarm> {
        val respuesta = alarmDao.getAllAlarms()
        return respuesta.map { it.toDomain() }
    }

    suspend fun insertAlarm(alarm:AlarmEntity){
        alarmDao.insertAll(listOf(alarm))
    }

}