package com.example.mobiletest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest.data.repository.DishesRepository
import com.example.mobiletest.data.models.CategoryModel
import com.example.mobiletest.data.models.DishesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//view model для блюд
@HiltViewModel
class DishesViewModel @Inject constructor(
    private val repository: DishesRepository
) : ViewModel() {
    private val _liveDataDishes = MutableLiveData<DishesModel>()
    val liveDataDishes: LiveData<DishesModel> = _liveDataDishes

    //аналогично с CategoryViewModel
    suspend fun getDishes() {
        viewModelScope.launch() {
            repository.deleteData()
            repository.getDishesModels()
            _liveDataDishes.postValue(repository.getDishesModelsFromDb())
        }
    }
}