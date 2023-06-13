package com.example.mobiletest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//view model для передачи данных в верхней панели
class SharedViewModel : ViewModel() {
    val data: MutableLiveData<String> = MutableLiveData()
}