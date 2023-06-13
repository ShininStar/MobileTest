package com.example.mobiletest.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobiletest.data.models.Category
import com.google.gson.annotations.SerializedName

//модель категорий
@Entity(tableName = "category_table")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("сategories")
    val categories: List<Category>
)