package com.example.alarmcristian.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alarmcristian.data.database.DatabaseBuilder
import com.example.alarmcristian.data.database.dao.AlarmDao
import com.example.alarmcristian.data.database.entities.AlarmEntity
import com.example.alarmcristian.databinding.FragmentDosificacionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class Dosificacion : Fragment() {

    private lateinit var binding: FragmentDosificacionBinding
    private lateinit var alarmDao: AlarmDao  // Declaramos el DAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el DAO usando el DatabaseBuilder
        val database = DatabaseBuilder.getDatabase(requireContext())
        alarmDao = database.getAlarmDao()  // Accedemos al DAO

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usamos el ViewBinding para inflar la vista
        binding = FragmentDosificacionBinding.inflate(inflater, container, false)

        // Configurar el click del botón
        binding.button.setOnClickListener {
            // Crear una nueva alarma con la fecha actual
            val newAlarm = AlarmEntity(
                date = Date() // La fecha actual
            )

            // Insertar la alarma en la base de datos en un hilo de fondo
            CoroutineScope(Dispatchers.IO).launch {
                alarmDao.insertAlarm(newAlarm) // Insertamos el dato en la base de datos
                withContext(Dispatchers.Main) {
                    // Aquí podrías mostrar un mensaje o realizar alguna acción tras la inserción
                    // Por ejemplo, mostrar un mensaje de éxito
                }
            }
        }

        return binding.root  // Devolvemos la vista inflada
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
