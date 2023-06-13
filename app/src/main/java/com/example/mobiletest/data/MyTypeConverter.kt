package com.example.mobiletest.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.mobiletest.data.models.Category
import com.example.mobiletest.data.models.Dishe

//тайп конвертер, чтобы перевести списко с объектами в строку и обратно для базы данных
class MyTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromListCategoryString(json: String) : List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toListCategoryString (categories: List<Category>) : String {
        return gson.toJson(categories)
    }

    @TypeConverter
    fun fromListDishesString(json: String) : List<Dishe> {
        val listType = object : TypeToken<List<Dishe>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toListDisheString (dishes: List<Dishe>) : String {
        return gson.toJson(dishes)
    }
}