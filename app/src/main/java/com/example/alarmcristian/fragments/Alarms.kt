package com.example.alarmcristian.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alarmcristian.data.database.DatabaseBuilder
import com.example.alarmcristian.data.database.dao.AlarmDao
import com.example.alarmcristian.data.database.entities.AlarmEntity
import com.example.alarmcristian.databinding.FragmentAlarmsBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class Alarms : Fragment() {
    lateinit var binding: FragmentAlarmsBinding
    lateinit var alarmDao: AlarmDao
    var fechaSeleccionada: AlarmEntity = AlarmEntity(0, Date());
    lateinit var picker: MaterialTimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseBuilder.getDatabase(requireContext())
        alarmDao = database.getAlarmDao()  // Accedemos al DAO
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = binding.calendarView

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate: AlarmEntity = AlarmEntity(0, Date(year, month, dayOfMonth))
            handleDateSelection(selectedDate, year, month)
        }
        binding.btnAgregar.setOnClickListener {
            showTimePicker()
//            CoroutineScope(Dispatchers.IO).launch {
//                alarmDao.insertAlarm(fechaSeleccionada)
//                withContext(Dispatchers.Main) {
//
//                }
//            }
        }
    }

    private fun handleDateSelection(selectedDate: AlarmEntity, year: Int, month: Int) {
        val currentCalendar = Calendar.getInstance()
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentYear = currentCalendar.get(Calendar.YEAR)

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            Toast.makeText(
                requireContext(),
                "La fecha seleccionada es anterior a la actual",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            fechaSeleccionada = selectedDate
            // Crear un objeto Calendar con la fecha seleccionada
//            val calendar = Calendar.getInstance()
//            calendar.set(selectedDate.date)

            // Crear el objeto AlarmEntity con la fecha seleccionada
//            val alarmEntity = AlarmEntity(date = calendar.time)

            // Ahora asignamos la fecha seleccionada al campo fechaSeleccionada
//            fechaSeleccionada = alarmEntity

        }
    }

    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12) // Opcional: Hora inicial
            .setMinute(0) // Opcional: Minuto inicial
            .setTitleText("Selecciona la hora")
            .build()

        picker.show(parentFragmentManager, "timePicker")

        picker.addOnPositiveButtonClickListener {
            // Combinamos la hora y la fecha seleccionada
            val calendar = Calendar.getInstance()

            // Asignamos la hora y minuto seleccionados
            calendar.set(Calendar.HOUR_OF_DAY, picker.hour)
            calendar.set(Calendar.MINUTE, picker.minute)

            // Si ya tienes la fecha seleccionada de otro lugar, la combinamos con la hora
            val fechaCompleta = fechaSeleccionada.date // La fecha seleccionada del calendario
            calendar.set(Calendar.YEAR, fechaCompleta.year + 1900) // Asegurarse de ajustar el año
            calendar.set(Calendar.MONTH, fechaCompleta.month)
            calendar.set(Calendar.DAY_OF_MONTH, fechaCompleta.date)

            // Creamos la nueva fecha con la hora y la fecha seleccionada
            val nuevaFecha = calendar.time // Esta es la fecha completa con la hora seleccionada

            // Ahora, asignamos esa fecha a la entidad AlarmEntity
            fechaSeleccionada = AlarmEntity(date = nuevaFecha)

            // Insertamos la alarma en la base de datos
            CoroutineScope(Dispatchers.IO).launch {
                alarmDao.insertAlarm(fechaSeleccionada) // Insertamos la alarma en la base de datos
                withContext(Dispatchers.Main) {
                    // Aquí puedes mostrar un mensaje o realizar alguna acción tras la inserción
                    Toast.makeText(requireContext(), "Alarma guardada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
