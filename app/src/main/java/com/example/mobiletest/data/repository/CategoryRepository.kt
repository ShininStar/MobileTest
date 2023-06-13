package com.example.mobiletest.data.repository

import com.example.mobiletest.data.models.CategoryModel

//интерфейс репозитория для категорий
interface CategoryRepository {
    suspend fun insertCategoryModel(categoryModel: CategoryModel)
    suspend fun getCategoryModels()
    suspend fun getCategoryModelsFromDb() : CategoryModel
    suspend fun deleteData()
}