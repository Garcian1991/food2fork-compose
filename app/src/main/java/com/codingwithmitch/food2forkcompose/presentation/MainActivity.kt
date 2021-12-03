package com.codingwithmitch.food2forkcompose.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codingwithmitch.food2forkcompose.datastore.SettingsProvider
import com.codingwithmitch.food2forkcompose.presentation.navigation.Screen
import com.codingwithmitch.food2forkcompose.presentation.ui.recipe.RecipeDetailScreen
import com.codingwithmitch.food2forkcompose.presentation.ui.recipe.RecipeViewModel
import com.codingwithmitch.food2forkcompose.presentation.ui.recipe_list.RecipeListScreen
import com.codingwithmitch.food2forkcompose.presentation.ui.recipe_list.RecipeListViewModel
import com.codingwithmitch.food2forkcompose.presentation.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var settingsProvider: SettingsProvider

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val isDarkTheme by settingsProvider.isDarkTheme.collectAsState(initial = false)
            val scope = rememberCoroutineScope()
            NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
                composable(route = Screen.RecipeList.route) { navBackStackEntry ->
                    val viewModel = hiltViewModel<RecipeListViewModel>()
                    RecipeListScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { scope.launch { settingsProvider.switchToDarkTheme(!isDarkTheme) } },
                        onNavigateToRecipeDetailScreen = navController::navigate,
                        viewModel = viewModel,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable
                    )

                }
                composable(
                    route = Screen.RecipeDetail.route + "/{recipeId}",
                    arguments = listOf(
                        navArgument("recipeId") {
                            type = NavType.IntType
                        })
                ) { navBackStackEntry ->
                    val viewModel = hiltViewModel<RecipeViewModel>()
                    RecipeDetailScreen(
                        isDarkTheme = isDarkTheme,
                        recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
                        viewModel = viewModel,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable
                    )
                }
            }
        }
    }
}


