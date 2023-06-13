package com.example.mobiletest.data.repository

import com.example.mobiletest.data.CategoryDao
import com.example.mobiletest.data.api.MainApi
import com.example.mobiletest.data.models.CategoryModel
import javax.inject.Inject

//реализация интерфейса
class CategoryRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val categoryDao: CategoryDao
    ) : CategoryRepository {

    //добавление модели в базу
    override suspend fun insertCategoryModel(categoryModel: CategoryModel) {
        categoryDao.insertCategoryModel(categoryModel)
    }

    //делаем запрос на сервер и сразу добавляем его в базу
    override suspend fun getCategoryModels() {
        return categoryDao.insertCategoryModel(mainApi.getCategory())
    }

    //достаем моедль из базы
    override suspend fun getCategoryModelsFromDb(): CategoryModel {
        return categoryDao.getCategoryModels()
    }

    //очищаем базу
    override suspend fun deleteData() {
        categoryDao.deleteData()
    }
}