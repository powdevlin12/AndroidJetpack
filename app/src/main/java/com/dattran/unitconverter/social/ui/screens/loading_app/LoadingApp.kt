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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dattran.unitconverter.social.data.local.UserPreferences
import com.dattran.unitconverter.social.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingApp(userPreferences: UserPreferences, navController: NavController) {
    val isAuth by userPreferences.isAuth.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(isAuth) {
        when (isAuth) {
            true -> navController.navigate(Screen.Home.route) {
                popUpTo(0) { inclusive = true } // xóa back stack
            }

            false -> navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }

            null -> Unit
        }
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