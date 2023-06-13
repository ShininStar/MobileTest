package com.example.mobiletest.data.api

import com.example.mobiletest.data.models.CategoryModel
import com.example.mobiletest.data.models.DishesModel
import retrofit2.http.GET

/*интерфейс запроса на сервер*/
interface MainApi {

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategory(): CategoryModel

    @GET("c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes(): DishesModel
}