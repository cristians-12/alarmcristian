package com.example.alarmcristian.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.alarmcristian.databinding.FragmentAlarmsBinding
import java.util.Calendar
import java.util.Date

class Alarms : Fragment() {
    lateinit var binding: FragmentAlarmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_alarms, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val spinner = binding.weekSpinner

        val calendarView = binding.calendarView

        val options = listOf("semana 1", "semana 2", "semana 3", "semana 4", "semana 5")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            options
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter

//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                val selectedOption = options[position]
//
//                Toast.makeText(
//                    requireContext(),
//                    "Seleccionaste: $selectedOption",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            handleDateSelection(selectedDate, year, month)

        }
    }

    private fun handleDateSelection(selectedDate: String, year: Int, month: Int) {

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
            Toast.makeText(
                requireContext(),
                "Fecha seleccionada: $selectedDate",
                Toast.LENGTH_SHORT
            ).show()

            val targetFragment = Dosificacion()
            parentFragmentManager.beginTransaction()
                .replace(binding.container.id, targetFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun replaceFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
    }
}