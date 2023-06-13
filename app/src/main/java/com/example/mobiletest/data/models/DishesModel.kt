package com.example.mobiletest.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobiletest.data.models.Dishe
import kotlinx.parcelize.Parcelize

//иодель блюд
@Entity(tableName = "dishes_table")
data class DishesModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val dishes: List<Dishe>
)