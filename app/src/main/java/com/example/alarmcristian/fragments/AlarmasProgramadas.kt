package com.example.alarmcristian.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alarmcristian.R
import com.example.alarmcristian.data.database.DatabaseBuilder
import com.example.alarmcristian.data.database.dao.AlarmDao
import com.example.alarmcristian.data.database.entities.AlarmEntity
import com.example.alarmcristian.databinding.ActivityMainBinding
import com.example.alarmcristian.databinding.FragmentAlarmasProgramadasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AlarmasProgramadas : Fragment() {

    private lateinit var binding: FragmentAlarmasProgramadasBinding
    private lateinit var alarmDao: AlarmDao
    private lateinit var alarmList: List<AlarmEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmasProgramadasBinding.inflate(inflater, container, false)

        // Inicializa la base de datos y el DAO
        val database = DatabaseBuilder.getDatabase(requireContext())
        alarmDao = database.getAlarmDao()

        // Cargar los registros desde la base de datos en un hilo de fondo
        CoroutineScope(Dispatchers.IO).launch {
            alarmList = alarmDao.getAllAlarms()
            withContext(Dispatchers.Main) {
                // Mostrar los datos (en este caso los imprimimos en un TextView)
                binding.alarmListTextView.text = formatAlarms(alarmList)
            }
        }

        return binding.root
    }

    // Formatear los registros para mostrarlos
    private fun formatAlarms(alarms: List<AlarmEntity>): String {
        return alarms.joinToString(separator = "\n") { alarm ->
            "ID: ${alarm.id}, Time: ${alarm.date}"
        }
    }
}

