package com.dattran.unitconverter

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dattran.unitconverter.movie_project.data.local.UserPreferences
import com.dattran.unitconverter.movie_project.navigation.NavGraph
import com.dattran.unitconverter.movie_project.ui.screens.login.LoginViewModel
import com.dattran.unitconverter.mvvm.NavigationMVVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // ⭐ Initialize dependencies
    private lateinit var userPreferences: UserPreferences
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ⭐ BƯỚC 1: Install splash screen TRƯỚC super.onCreate()
        val splashScreen = installSplashScreen()

        // ⭐ BƯỚC 2: Keep splash screen visible while loading (optional)
        // Splash sẽ hiện cho đến khi condition = false
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.light(
//                android.graphics.Color.TRANSPARENT,
//                android.graphics.Color.TRANSPARENT,
//            )
        )
        userPreferences = UserPreferences(applicationContext)
        loginViewModel = LoginViewModel(userPreferences)
        // ⭐ BƯỚC 3: Sau khi load xong, ẩn splash
        keepSplashScreen = false

        setContent {
            setStatusBarColor(color = Color.Red)
            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        userPreferences = userPreferences,
                        loginViewModel = loginViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun setStatusBarColor(color: Color) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
        }
    }
    // Implementation for setting status bar color if needed
}