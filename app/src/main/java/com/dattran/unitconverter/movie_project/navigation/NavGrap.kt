package com.dattran.unitconverter.movie_project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dattran.unitconverter.movie_project.data.local.UserPreferences
import com.dattran.unitconverter.movie_project.ui.screens.create_movie.CreateMovieScreen
import com.dattran.unitconverter.movie_project.ui.screens.home.HomeScreen
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.HomeQTV
import com.dattran.unitconverter.movie_project.ui.screens.login.LoginScreen
import com.dattran.unitconverter.movie_project.ui.screens.login.LoginViewModel
import com.dattran.unitconverter.movie_project.ui.screens.register.Register
import com.dattran.unitconverter.movie_project.ui.screens.update_movie.UpdateMovieScreen
import kotlinx.coroutines.flow.first

sealed class Screen(val route: String) {
    object Register : Screen("register")
    object Login : Screen("login")
    object Home : Screen("home")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }

    object Create : Screen("create") {
        fun createRoute() = "create"
    }

    object Update : Screen("update/{movieId}") {
        fun createRoute(movieId: String) = "update/$movieId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    userPreferences: UserPreferences,
    loginViewModel: LoginViewModel
) {
    // ⭐ Conditional start destination
    val startDestination = remember {
        // Lấy giá trị đồng bộ từ Flow (blocking call trong remember)
        kotlinx.coroutines.runBlocking {
            val isLoggedIn = userPreferences.isAuth.first()
            if (isLoggedIn == true) Screen.Home.route else Screen.Login.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController, viewModel = loginViewModel)
        }

        composable(Screen.Register.route) {
            Register()
        }

        composable(Screen.Home.route) {
//            HomeScreen(
//                navController,
//                userPreferences = userPreferences
//            )
            HomeQTV(navController)
        }

        composable(Screen.Create.route) {
            CreateMovieScreen(
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