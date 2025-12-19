package com.dattran.unitconverter.movie_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dattran.unitconverter.movie_project.ui.screens.home.HomeScreen
import com.dattran.unitconverter.movie_project.ui.screens.update_movie.UpdateMovieScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }

    object Update : Screen("update/{movieId}") {
        fun createRoute(movieId: String) = "update/$movieId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController
            )
        }

        composable(
            route = Screen.Update.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType }
            )
        ) { entry ->
            val movieId = entry.arguments?.getString("movieId") ?: "000"
            UpdateMovieScreen(
                movieId = movieId,
                navController = navController
            )
        }
    }
}