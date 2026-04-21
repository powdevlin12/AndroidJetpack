package com.dattran.unitconverter

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.dattran.unitconverter.navigation.Navigation
import com.dattran.unitconverter.social.data.local.AppDatabase
import com.dattran.unitconverter.social.data.local.UserPreferences
import com.dattran.unitconverter.social.data.repository.TweetRepository
import com.dattran.unitconverter.social.data.repository.UserRepository
import com.dattran.unitconverter.social.data.service.AuthApiService
import com.dattran.unitconverter.social.navigation.NavGraph
import com.dattran.unitconverter.social.ui.screens.edit_profile.EditProfileViewModel
import com.dattran.unitconverter.social.ui.screens.login.LoginViewModel
import com.dattran.unitconverter.social.ui.screens.post_tweet.PostTweetViewModel
import com.dattran.unitconverter.social.ui.screens.profile.ProfileViewModel
import com.dattran.unitconverter.social.ui.screens.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

        // Khởi tạo UserPreferences và database trước khi setContent
        val userPreferences = UserPreferences(applicationContext)
        // ⭐ Get database instance and UserDao
        val database = AppDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()
        val apiService = AuthApiService.create()
        val userInfoRepository = UserRepository(userDao = userDao, apiService)
        val tweetRepository = TweetRepository(apiService = apiService, userDao = userDao)

        val loginViewModel =
            LoginViewModel(userPreferences, userInfoRepository = userInfoRepository)
        val profileViewModel = ProfileViewModel(userDao = userDao)
        val editProfileViewModel = EditProfileViewModel(userDao = userDao)
        val registerViewModel = RegisterViewModel();
        val postTweetViewModel = PostTweetViewModel(repository = tweetRepository);

        // ⭐ BƯỚC 3: Sau khi load xong, ẩn splash
        keepSplashScreen = false

        setContent {
            setStatusBarColor(color = Color(0xFFFFFFFF))
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val navController = rememberNavController()

                // ⭐ XỬ LÝ DEEPLINK
                LaunchedEffect(intent) {
                    handleDeepLink(intent, navController)
                }

                NavGraph(
                    navController = navController,
                    userPreferences = userPreferences,
                    loginViewModel = loginViewModel,
                    profileViewModel = profileViewModel,
                    editProfileViewModel = editProfileViewModel,
                    registerViewModel = registerViewModel,
                    postTweetViewModel = postTweetViewModel
                )
            }
        }

    }

    // ⭐ Xử lý khi có intent mới (khi app đang chạy)
    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    // ⭐ Hàm xử lý deeplink
    private fun handleDeepLink(
        intent: android.content.Intent?,
        navController: androidx.navigation.NavHostController
    ) {
        val data = intent?.data
        if (data != null && data.scheme == "loyaltyapp") {
            when (data.host) {
                "main" -> {
                    // loyaltyapp://main/register
                    val path = data.pathSegments.firstOrNull()
                    when (path) {
                        "register" -> navController.navigate("register") {
                            launchSingleTop = true
                        }

                        "login" -> navController.navigate("login") {
                            launchSingleTop = true
                        }
                        // Thêm các path khác nếu cần
                    }
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