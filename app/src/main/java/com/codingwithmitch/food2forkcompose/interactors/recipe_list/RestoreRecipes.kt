package com.codingwithmitch.food2forkcompose.interactors.recipe_list

import android.util.Log
import com.codingwithmitch.food2forkcompose.cache.RecipeDao
import com.codingwithmitch.food2forkcompose.cache.model.RecipeEntityMapper
import com.codingwithmitch.food2forkcompose.domain.data.DataState
import com.codingwithmitch.food2forkcompose.domain.model.Recipe
import com.codingwithmitch.food2forkcompose.util.RECIPE_PAGINATION_PAGE_SIZE
import com.codingwithmitch.food2forkcompose.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class RestoreRecipes(
    private val recipeDao: RecipeDao,
    private val entityMapper: RecipeEntityMapper
) {
    fun execute(
        page: Int,
        query: String
    ): Flow<DataState<List<Recipe>>> {
        return flow {
            try {
                emit(DataState.loading())

                delay(1000)

                val cacheResult = if (query.isBlank()) {
                    recipeDao.restoreAllRecipes(page = page, pageSize = RECIPE_PAGINATION_PAGE_SIZE)
                } else {
                    recipeDao.restoreRecipes(
                        query = query,
                        page = page,
                        pageSize = RECIPE_PAGINATION_PAGE_SIZE
                    )
                }

                val list = entityMapper.fromEntityList(cacheResult)

                emit(DataState.success(list))
            } catch (e: Exception) {
                Log.e(TAG, "execute: ${e.message}")
                emit(DataState.error(e.message ?: "Unknown error"))
            }
        }
    }
}