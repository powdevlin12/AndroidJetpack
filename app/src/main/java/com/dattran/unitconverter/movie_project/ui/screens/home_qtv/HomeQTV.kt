package com.dattran.unitconverter.movie_project.ui.screens.home_qtv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.BarcodeCard
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.FeatureList
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.PromotionSection
import com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components.StatsCard

@Preview
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
            Column(modifier = Modifier.fillMaxSize().background(color = Color.White).padding(bottom = 80.dp)) {
                // Stats Cards Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Points Card
                    StatsCard(
                        value = userStats.points,
                        unit = "điểm",
                        label = "Xem lịch sử",
                        emoji = "💰",
                        backgroundColor = Color(0xFFFFF9E6),
                        borderColor = Color(0xFFFFCC00),
                        modifier = Modifier.weight(1f)
                    )

                    // Gifts Card
                    StatsCard(
                        value = userStats.gifts.toString(),
                        unit = "quà tặng",
                        label = "Xem quà",
                        emoji = "🎁",
                        backgroundColor = Color(0xFFFFF0F0),
                        borderColor = Color(0xFFFFCC00),
                        modifier = Modifier.weight(1f)
                    )
                }

                FeatureList(features)
                PromotionSection(banners)
                PromotionSection(banners)
            }
        }
    }
}

