package com.example.alarmcristian.di

import android.content.Context
import androidx.room.Room
import com.example.alarmcristian.data.database.AlarmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "alarm_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AlarmDatabase {
        return Room.databaseBuilder(context, AlarmDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideAlarmDao(db: AlarmDatabase) = db.getAlarmDao()
}