package com.codingwithmitch.food2forkcompose.cash.database

import androidx.room.Dao
import androidx.room.Insert
import com.codingwithmitch.food2forkcompose.cash.model.RecipeEntity

@Dao
interface RecipeDAO {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long
}