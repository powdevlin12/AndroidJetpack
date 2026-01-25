package com.dattran.unitconverter.social.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dattran.unitconverter.social.ui.components.BottomNavigationBar

/**
 * MainScreen - Container màn hình chính với BottomNavigationBar
 * Tất cả các màn hình con sẽ được hiển thị trong màn hình này
 */
@Composable
fun MainScreen(
    navController: NavController,
    currentRoute: String?,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Nội dung màn hình con
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            content()
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            selectedRoute = currentRoute ?: "home",
            onItemSelected = { route ->
                // Điều hướng đến route tương ứng
                navController.navigate(route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

