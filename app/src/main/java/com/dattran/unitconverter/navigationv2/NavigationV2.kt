package com.dattran.unitconverter.navigationv2

import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dattran.unitconverter.uitabpager.HomeScreen

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Trang chủ")
    object Profile : Screen("profile", "Hồ sơ")
    object Setting : Screen("settings", "Cài đặt")

    data class Detail(val id: String) : Screen("detail", "Chi tiết") {
        fun createRoute() = "detail/$id"
    }

    data class UserProfile(val userId: Int, val tab: String) :
        Screen("user_profile", "Hồ sơ người dùng") {
        fun createRoute() = "user_profile/$userId/$tab"
    }

    data class Search(val query: String, val category: String = "all") :
        Screen("search", "Tìm kiếm") {
        fun createRoute() = "search/$query/$category"
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        /* composable(Screen.Profile.route) {
             ProfileScreen(navController)
         }*/

        composable(
            route = "detail/{id}", arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )) { entry ->
            val id = entry.arguments?.getString("id") ?: ""
            DetailScreen(navController, id)
        }

        composable(
            route = "profile/{userId}/{tab}", arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = 0
                },
                navArgument("tab") {
                    type = NavType.StringType
                    defaultValue = "info"
                }
            )
        ) { entry ->
            val userId = entry.arguments?.getInt("userId") ?: 0
            val tab = entry.arguments?.getString("tab") ?: "info"
            UserProfileScreen(navController, userId, tab)
        }

        // Màn hình Search - CÓ arguments
        composable(
            route = "search/{query}/{category}",
            arguments = listOf(
                navArgument("query") { type = NavType.StringType },
                navArgument("category") {
                    type = NavType.StringType
                    defaultValue = "all"
                }
            )
        ) { entry ->
            val query = entry.arguments?.getString("query") ?: ""
            val category = entry.arguments?.getString("category") ?: "all"
            SearchScreen(navController, query, category)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        Text("Trang Chủ", style = MaterialTheme.typography.headlineMedium)

        // Navigation với object (không có tham số)
        TextButton(onClick = {
            navController.navigate(Screen.Profile.route)
        }) {
            Text("Đến Profile")
        }

        // Navigation với data class (có tham số)
        TextButton(onClick = {
            val detailScreen = Screen.Detail(id = "product_123")
            navController.navigate(detailScreen.createRoute())
        }) {
            Text("Đến Detail với ID")
        }

        // Navigation với nhiều tham số
        TextButton(onClick = {
            val userProfile = Screen.UserProfile(userId = 456, tab = "settings")
            navController.navigate(userProfile.createRoute())
        }) {
            Text("Đến User Profile")
        }

        // Navigation với tham số tùy chọn
        TextButton(onClick = {
            val search = Screen.Search(query = "kotlin", category = "programming")
            navController.navigate(search.createRoute())
        }) {
            Text("Tìm kiếm Kotlin")
        }
    }
}

@Composable
fun DetailScreen(navController: NavController, id: String) {
    Column {
        Text("Chi Tiết: $id", style = MaterialTheme.typography.headlineMedium)
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Quay lại")
        }
    }
}

@Composable
fun UserProfileScreen(navController: NavController, userId: Int, tab: String) {
    Column {
        Text("User ID: $userId", style = MaterialTheme.typography.headlineMedium)
        Text("Tab: $tab", style = MaterialTheme.typography.bodyLarge)

        // Chuyển tab ngay trong cùng màn hình
        Row {
            listOf("info", "posts", "settings").forEach { tabName ->
                TextButton(
                    onClick = {
                        val newRoute = Screen.UserProfile(userId, tabName).createRoute()
                        navController.navigate(newRoute) {
                            // Quan trọng: popUpTo để tránh stack tràn
                            popUpTo(Screen.UserProfile(userId, tab).route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tab == tabName) Color.Blue else Color.Gray
                    )
                ) {
                    Text(tabName)
                }
            }
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Quay lại")
        }
    }
}

@Composable
fun SearchScreen(navController: NavController, query: String, category: String) {
    Column {
        Text("Tìm kiếm: $query", style = MaterialTheme.typography.headlineMedium)
        Text("Danh mục: $category", style = MaterialTheme.typography.bodyLarge)

        // Hiển thị kết quả tìm kiếm dựa trên query và category
        LazyColumn {
            items(10) { index ->
                Text("Kết quả $index cho '$query' trong '$category'")
            }
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Quay lại")
        }
    }
}




















