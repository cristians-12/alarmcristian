package com.example.alarmcristian.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmcristian.domain.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AlarmViewModel @Inject constructor(

) : ViewModel() {

    val alarmModel = MutableLiveData<Alarm>()

    fun onCreate(){

    }
}