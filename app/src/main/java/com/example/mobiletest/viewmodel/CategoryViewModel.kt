package com.example.mobiletest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest.data.repository.CategoryRepository
import com.example.mobiletest.data.models.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//вью модель для категорий
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _liveDataCategory = MutableLiveData<CategoryModel>()
    val liveDataCategory: LiveData<CategoryModel> = _liveDataCategory

    /*функция запроса на сервер и получения из базы данных, сохраняем в лайв дата*/
    suspend fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData()
            repository.getCategoryModels()
            val items = repository.getCategoryModelsFromDb()
            _liveDataCategory.postValue(items)
        }
    }
}

