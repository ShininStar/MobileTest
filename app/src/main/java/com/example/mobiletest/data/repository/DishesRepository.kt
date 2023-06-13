package com.example.mobiletest.data.repository

import com.example.mobiletest.data.models.DishesModel

//интерфейс репозитория для блюд
interface DishesRepository {
    suspend fun insertDishesModel(dishesModel: DishesModel)
    suspend fun getDishesModels()
    suspend fun getDishesModelsFromDb() : DishesModel
    suspend fun deleteData()
}