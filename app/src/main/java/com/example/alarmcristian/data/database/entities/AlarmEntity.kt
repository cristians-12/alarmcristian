package com.example.alarmcristian.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarmcristian.domain.model.Alarm
import java.util.Date

@Entity(tableName = "alarms_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "date") val date: Date
)
fun Alarm.toDatabase() = AlarmEntity(date = date)