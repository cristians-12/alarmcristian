package com.example.alarmcristian.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.alarmcristian.R
import com.example.alarmcristian.R.id.parentDosificacion


class Dosificacion : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dosificacion, container, false)
        return view // No need to adjust layoutParams here    val view = inflater.inflate(R.layout.fragment_dosificacion, container, false)
        return view // No need to adjust layoutParams here
    }

}