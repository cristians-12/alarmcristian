package com.example.alarmcristian.fragments

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.ALARM_SERVICE
import androidx.core.app.NotificationCompat
import com.example.alarmcristian.AlarmReceiver
import com.example.alarmcristian.MainActivity
import com.example.alarmcristian.R
import com.example.alarmcristian.databinding.FragmentHomeBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el botón o acciones que necesiten el binding aquí
        binding.showHourBtn.setOnClickListener {
            showTimePicker()
        }

        binding.setAlarmBtn.setOnClickListener {
            setAlarm()
        }

        binding.cancelBtn.setOnClickListener {
            cancelAlarm()
        }

        binding.notificationBtn.setOnClickListener {
            showNotification()
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "alarmcristian"
            val name = "Alarm Notifications"
            val description = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH // Asegúrate de que la importancia sea alta

            // Crear el canal de notificación
            val channel = NotificationChannel(channelId, name, importance).apply {
                this.description = description
            }

            // Obtener el NotificationManager
            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)

            // Crear el canal
            notificationManager?.createNotificationChannel(channel)
        }
    }



    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Time")
            .build()

        picker.show(parentFragmentManager, "timePicker")

        picker.addOnPositiveButtonClickListener {
            val selectedHour = if (picker.hour > 12) picker.hour - 12 else picker.hour
            val period = if (picker.hour >= 12) "PM" else "AM"

            binding.selectedTime.text = String.format(
                "%02d:%02d %s",
                selectedHour,
                picker.minute,
                period
            )

            calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, picker.hour)
                set(Calendar.MINUTE, picker.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }
    }

    private fun setAlarm() {
        if (!this::calendar.isInitialized) {
            Toast.makeText(
                requireContext(),
                "Por favor selecciona una hora primero",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Ensure alarm time is in the future
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(requireContext(), "Alarma configurada exitosamente", Toast.LENGTH_SHORT)
            .show()
    }

    private fun cancelAlarm() {
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        alarmManager.cancel(pendingIntent)

        Toast.makeText(requireContext(), "Alarma cancelada", Toast.LENGTH_SHORT).show()

    }


    private fun showNotification() {
        val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
        val notificationIntent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(requireContext(), "alarmcristian")
            .setSmallIcon(R.drawable.ic_alarm) // Reemplaza por un ícono real
            .setContentTitle("Alarma Activada")
            .setContentText("¡La alarma se ha configurado correctamente!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (notificationManager != null) {
            notificationManager.notify(123, notification)
            Log.d("Notification", "Notification Manager is not null")
        } else {
            Log.e("Notification", "Notification Manager is null")
        }
    }
}
