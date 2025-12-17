package com.dattran.unitconverter.movie_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dattran.unitconverter.movie_project.ui.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
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
                
            )
        }

//        composable(
//            route = Screen.Detail.route,
//            arguments = listOf(
//                navArgument("movieId") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
//            DetailScreen(
//                movieId = movieId,
//                onBackClick = { navController.popBackStack() }
//            )
//        }
    }
}