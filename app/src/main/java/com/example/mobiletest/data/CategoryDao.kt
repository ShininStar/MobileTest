package com.example.mobiletest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobiletest.data.models.CategoryModel

//дао для категорий
@Dao
interface CategoryDao {

    //добавляем моедль в базу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryModel(categoryModel: CategoryModel)

    //достаем модель из базы
    @Query("SELECT * FROM category_table")
    suspend fun getCategoryModels() : CategoryModel

    //удаляем все из базы
    @Query("DELETE FROM category_table")
    suspend fun deleteData()
}