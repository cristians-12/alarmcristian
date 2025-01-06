package com.example.alarmcristian.data.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var appDatabase: AlarmDatabase? = null

    fun getDatabase(context: Context): AlarmDatabase {
        return appDatabase ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                "alarm_database"
            ).build()
            appDatabase = instance
            instance
        }
    }
}