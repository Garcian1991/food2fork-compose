package com.codingwithmitch.food2forkcompose.cash.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingwithmitch.food2forkcompose.cash.RecipeDao
import com.codingwithmitch.food2forkcompose.cash.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        val DATABASE_NAME = "recipe_db"
    }
}