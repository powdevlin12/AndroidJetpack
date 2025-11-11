package com.dattran.unitconverter.mvvm

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dattran.unitconverter.mvvm.ui.screens.detailscreen.DetailScreenMVVM
import com.dattran.unitconverter.mvvm.ui.screens.homescreen.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen")
}

@Composable
fun NavigationMVVM() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController, hiltViewModel())
        }
        composable(Screen.Detail.route) {
            DetailScreenMVVM(navController, hiltViewModel())
        }
    }

}