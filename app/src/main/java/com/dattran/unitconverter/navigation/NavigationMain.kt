package com.dattran.unitconverter.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Detail : Screen("detail", "Detail")
}

@Composable
fun HomeScreen(navContronller: androidx.navigation.NavController) {
    Column {
        TextButton(onClick = {
            navContronller.navigate("${Screen.Detail.route}/A/aa")
        }) {
            Text("Go to A")
        }
        TextButton(onClick = {
            navContronller.navigate("${Screen.Detail.route}/B/bcd")
        }) {
            Text("Go to B")
        }
    }
}

@Composable
fun DetailScreen(navContronller: NavController, arg1: String, arg2: String) {
    Column {
        Text("arg1: $arg1")
        Text("arg2: $arg2")
        TextButton(onClick = {
            navContronller.popBackStack()
        }) {
            Text("Back to Home")
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            Screen.Detail.route + "/{arg1}/{arg2}", arguments = listOf(
                navArgument("arg1") { type = NavType.StringType },
                navArgument("arg2") { type = NavType.StringType },
            )
        ) { entry ->
            DetailScreen(
                navController,
                entry.arguments?.getString("arg1") ?: "",
                entry.arguments?.getString("arg2") ?: ""
            )
        }
    }
}