package com.example.alarmcristian.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alarmcristian.data.database.converters.DateConverter
import com.example.alarmcristian.data.database.dao.AlarmDao
import com.example.alarmcristian.data.database.entities.AlarmEntity

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao

}