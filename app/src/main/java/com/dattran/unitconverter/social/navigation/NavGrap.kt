package com.dattran.unitconverter.social.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.dattran.unitconverter.social.data.local.UserPreferences
import com.dattran.unitconverter.social.ui.screens.MainScreen
import com.dattran.unitconverter.social.ui.screens.cart.CartScreen
import com.dattran.unitconverter.social.ui.screens.category.CategoryScreen
import com.dattran.unitconverter.social.ui.screens.create_movie.CreateMovieScreen
import com.dattran.unitconverter.social.ui.screens.home_qtv.HomeQTV
import com.dattran.unitconverter.social.ui.screens.login.LoginScreen
import com.dattran.unitconverter.social.ui.screens.login.LoginViewModel
import com.dattran.unitconverter.social.ui.screens.profile.ProfileScreen
import com.dattran.unitconverter.social.ui.screens.register.Register
import com.dattran.unitconverter.social.ui.screens.update_movie.UpdateMovieScreen
import kotlinx.coroutines.flow.first

sealed class Screen(val route: String) {
    object Register : Screen("register")
    object Login : Screen("login")
    object Home : Screen("home")
    object Category : Screen("category")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
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
    // ⭐ Get current route để biết có hiển thị BottomNavigationBar không
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Màn hình không có BottomNavigationBar
        composable(Screen.Login.route) {
            LoginScreen(navController, viewModel = loginViewModel)
        }

        composable(Screen.Register.route) {
            Register()
        }

        // ⭐ Các màn hình có BottomNavigationBar
        composable(Screen.Home.route) {
            MainScreen(navController, currentRoute) {
                HomeQTV(navController)
            }
        }

        composable(Screen.Category.route) {
            MainScreen(navController, currentRoute) {
                CategoryScreen(navController)
            }
        }

        composable(Screen.Cart.route) {
            MainScreen(navController, currentRoute) {
                CartScreen(navController)
            }
        }

        composable(Screen.Profile.route) {
            MainScreen(navController, currentRoute) {
                ProfileScreen(navController)
            }
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