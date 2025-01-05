package com.example.alarmcristian.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmcristian.data.database.dao.AlarmDao
import com.example.alarmcristian.data.database.entities.AlarmEntity

@Database(entities = [AlarmEntity::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun getAlarmDao(): AlarmDao

}