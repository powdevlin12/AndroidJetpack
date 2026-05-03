package com.dattran.unitconverter.social.ui.screens.loading_app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dattran.unitconverter.social.data.local.UserPreferences
import com.dattran.unitconverter.social.data.repository.UserRepository
import com.dattran.unitconverter.social.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingApp(
    userPreferences: UserPreferences, navController: NavController,
    loadingAppViewModel: LoadingAppViewModel
) {
    val isAuth by userPreferences.isAuth.collectAsStateWithLifecycle(initialValue = null)

    fun onSuccessful() {
        navController.navigate(Screen.Home.route) {
            popUpTo(0) { inclusive = true } // xóa back stack
        }
    }

    fun onFail() {
        navController.navigate(Screen.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    LaunchedEffect(Unit) {
        loadingAppViewModel.handleGetUserInfoWhenStartApp(
            onSuccessful = ::onSuccessful,
            onFail = ::onFail
        )
    }

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}