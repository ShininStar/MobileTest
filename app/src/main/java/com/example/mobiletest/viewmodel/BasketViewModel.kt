package com.example.mobiletest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobiletest.data.models.Basket
import com.example.mobiletest.data.models.Dishe

//вью модель для корзины
class BasketViewModel  : ViewModel() {
    val dishesInBasket = MutableLiveData<HashMap<Dishe, Int>>()
}