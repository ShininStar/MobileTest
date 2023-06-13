package com.example.mobiletest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobiletest.data.models.DishesModel

//дао для блюд
@Dao
interface DishesDao {
    //добавляем модель в базу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishesModel(dishesModel: DishesModel)

    //берем модель из базы
    @Query("SELECT * FROM dishes_table")
    suspend fun getDishesModels() : DishesModel

    //удаляем данные из базы
    @Query("DELETE FROM dishes_table")
    suspend fun deleteData()
}