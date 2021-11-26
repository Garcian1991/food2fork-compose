package com.codingwithmitch.food2forkcompose.presentation.navigation

sealed class Screen(
    val route: String
) {

    object RecipeList: Screen(RECIPE_LIST_SCREEN)

    object RecipeDetail: Screen(RECIPE_DETAILS)

    companion object {
        const val RECIPE_LIST_SCREEN = "recipeList"
        const val RECIPE_DETAILS = "recipeDetails"
    }
}
