package com.dattran.unitconverter.movie_project.ui.screens.home_qtv

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dattran.unitconverter.movie_project.ui.components.BottomNavigationBar
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.BarcodeCard
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.FeatureList
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.PointAndGift
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.PromotionSection
import com.dattran.unitconverter.movie_project.utils.NotificationHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Preview(showBackground = true)
@Composable
fun PreviewHomeQTV() {
    HomeQTV(navController = NavController(LocalContext.current))
}

@Composable
fun HomeQTV(
    navController: NavController,
    viewModel: HomeQTVViewModel = hiltViewModel()
) {
    val userStats by viewModel.userStats.collectAsState()
    val features by viewModel.features.collectAsState()
    val banners by viewModel.banners.collectAsState()
    var selectedRoute by remember { mutableStateOf("home") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFF5E1),
                            Color(0xFFFFE4E1),
                            Color(0xFFFFF8DC)
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
        ) {
            // Header with user name
            Text(
                text = userStats.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )

            // Barcode Card
            BarcodeCard(
                barcode = userStats.barcode,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-36).dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                        .padding(bottom = 80.dp)
                ) {
                    // Stats Cards Row
                    PointAndGift(userStats)
                    FeatureList(features)
                    PromotionSection(banners)
                    PromotionSection(banners)
                }
            }
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            selectedRoute = selectedRoute,
            onItemSelected = { route ->
                selectedRoute = route
                // Handle navigation here
                // navController.navigate(route)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

