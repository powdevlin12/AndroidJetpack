package com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dattran.unitconverter.movie_project.data.model.UserStats

@Composable
fun PointAndGift(userStats : UserStats) {
    // Implementation goes here
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
}