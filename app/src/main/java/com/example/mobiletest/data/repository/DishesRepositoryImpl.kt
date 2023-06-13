package com.example.mobiletest.data.repository

import com.example.mobiletest.data.DishesDao
import com.example.mobiletest.data.api.MainApi
import com.example.mobiletest.data.models.DishesModel
import javax.inject.Inject

//реализация интерфейса для блюд
class DishesRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val dishesDao: DishesDao
) : DishesRepository {
    //добавление моедли в базу данных
    override suspend fun insertDishesModel(dishesModel: DishesModel) {
        dishesDao.insertDishesModel(dishesModel)
    }
    //хапрос на сервер и добавление модели в базу
    override suspend fun getDishesModels() {
        dishesDao.insertDishesModel(mainApi.getDishes())
    }

    //запрос к базе данных
    override suspend fun getDishesModelsFromDb(): DishesModel {
        return dishesDao.getDishesModels()
    }

    //очистка базы
    override suspend fun deleteData() {
        dishesDao.deleteData()
    }
}