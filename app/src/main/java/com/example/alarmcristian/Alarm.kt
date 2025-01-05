package com.example.alarmcristian.utils

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class Alarm(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val onTimeSelected: (Calendar) -> Unit
) {

    public fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12) // Opcional: Hora inicial
            .setMinute(0) // Opcional: Minuto inicial
            .setTitleText("Selecciona la hora")
            .build()

        picker.show(fragmentManager, "timePicker")

        picker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            // Llama a la función callback cuando se selecciona la hora
            onTimeSelected(calendar)
        }
    }
}
