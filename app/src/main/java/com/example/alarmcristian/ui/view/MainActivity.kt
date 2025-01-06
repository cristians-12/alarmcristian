package com.example.alarmcristian.ui.view

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.alarmcristian.AlarmReceiver
import com.example.alarmcristian.R
import com.example.alarmcristian.databinding.ActivityMainBinding
import com.example.alarmcristian.fragments.AddAlarm
import com.example.alarmcristian.fragments.AlarmasProgramadas
import com.example.alarmcristian.fragments.Alarms
import com.example.alarmcristian.fragments.Home
import com.example.alarmcristian.utils.Alarm
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar


//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el diseño usando ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        val alarm = Alarm(this, supportFragmentManager) { selectedTime ->
            // Aquí puedes manejar el tiempo seleccionado, por ejemplo:
            binding.selectedTime.text = String.format(
                "%02d:%02d",
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE)
            )
        }

        // Configura el listener de BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    replaceFragment(Home())
                    true
                }

                R.id.ic_alarms -> {
                    replaceFragment(Alarms())
                    true
                }

                R.id.ic_add_alarm -> {
                    replaceFragment(AddAlarm())
                    true
                }

                R.id.ic_alarmas_programadas -> {
                    replaceFragment(AlarmasProgramadas())
                    true
                }

                else -> false
            }
        }
        // Carga el fragmento inicial
        if (savedInstanceState == null) {
            replaceFragment(Home())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "alarmcristianReminderChannel"
            val description = "Channel for Alarm Manager"
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val channel = android.app.NotificationChannel("alarmcristian", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
        }
    }

    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12) // Opcional: Hora inicial
            .setMinute(0) // Opcional: Minuto inicial
            .setTitleText("Selecciona la hora")
            .build()
        picker.show(supportFragmentManager, "timePicker")

        picker.addOnPositiveButtonClickListener() {
            if (picker.hour > 12) {
                binding.selectedTime.text =
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + "PM"
            } else {
                binding.selectedTime.text =
                    String.format("%02d", picker.hour - 12) + " : " + String.format(
                        "%02d",
                        picker.minute
                    ) + "AM"
            }
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }
    }

    private fun setAlarm() {
        if (!this::calendar.isInitialized) {
            Toast.makeText(this, "Por favor selecciona una hora primero", Toast.LENGTH_SHORT).show()
            return
        }

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(this, "Alarma configurada exitosamente", Toast.LENGTH_SHORT).show()
    }


}
