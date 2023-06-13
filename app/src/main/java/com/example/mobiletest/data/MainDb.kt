package com.example.mobiletest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobiletest.data.models.CategoryModel
import com.example.mobiletest.data.models.DishesModel

//наша база данных с двумя сущностями
@Database(
    entities = [CategoryModel::class, DishesModel::class],
    version = 1
)
@TypeConverters(MyTypeConverter::class)
abstract class MainDb: RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val dishesDao: DishesDao
}