package com.example.alarmcristian.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AlarmDB.db"
        private const val DATABASE_VERSION = 1

        // Nombre de la tabla y columnas
        const val TABLE_NAME = "alarms"
        const val COLUMN_ID = "id"
        const val COLUMN_TIME = "time"
        const val COLUMN_LABEL = "label"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TIME TEXT NOT NULL,
                $COLUMN_LABEL TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Actualizar base de datos
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Método para insertar un registro
    fun insertAlarm(time: String, label: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TIME, time)
            put(COLUMN_LABEL, label)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    // Método para obtener todos los registros
    fun getAllAlarms(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    // Método para actualizar un registro
    fun updateAlarm(id: Int, time: String, label: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TIME, time)
            put(COLUMN_LABEL, label)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    // Método para eliminar un registro
    fun deleteAlarm(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}
