package com.codingwithmitch.food2forkcompose.interactors.recipe

import android.util.Log
import com.codingwithmitch.food2fork.network.RecipeService
import com.codingwithmitch.food2forkcompose.cache.RecipeDao
import com.codingwithmitch.food2forkcompose.cache.model.RecipeEntityMapper
import com.codingwithmitch.food2forkcompose.domain.data.DataState
import com.codingwithmitch.food2forkcompose.domain.model.Recipe
import com.codingwithmitch.food2forkcompose.network.model.RecipeDtoMapper
import com.codingwithmitch.food2forkcompose.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipe(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val entityMapper: RecipeEntityMapper,
    private val recipeDtoMapper: RecipeDtoMapper
) {
    fun execute(
        recipeId: Int, token: String, isNetworkAvailable: Boolean
    ) : Flow<DataState<Recipe>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)
            var recipe = getRecipeFromCache(recipeId = recipeId)

            if (recipe != null) {
                emit(DataState.success(recipe))
            } else {
                if (isNetworkAvailable) {
                    val networkRecipe = getRecipeFromNetwork(token, recipeId)

                    recipeDao.insertRecipe(
                        entityMapper.mapFromDomainModel(networkRecipe)
                    )
                }

                recipe = getRecipeFromCache(recipeId)
                if (recipe != null) {
                    emit(DataState.success(recipe))
                } else {
                    throw Exception("Unable to get the recipe from the cache")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}", )
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getRecipeFromCache(recipeId: Int): Recipe? {
        return recipeDao.getRecipeById(recipeId)?.let { recipeEntity ->
            entityMapper.mapToDomainModel(recipeEntity)
        }
    }

    private suspend fun getRecipeFromNetwork(token: String, recipeId: Int): Recipe {
        return recipeService.get(token, recipeId).let { recipeDto ->
            recipeDtoMapper.mapToDomainModel(recipeDto)
        }
    }
}