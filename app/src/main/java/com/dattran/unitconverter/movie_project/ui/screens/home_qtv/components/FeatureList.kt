package com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.movie_project.data.model.FeatureItem

@Composable
fun  FeatureList(
    features :  List<FeatureItem>
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {
        // Tiện ích section
        Text(
            text = "Tiện ích",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF222222),
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
        )
        // Features Grid
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // First row of features
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                features.take(4).forEach { feature ->
                    FeatureItemCard(
                        feature = feature,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Second row of features
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                features.drop(4).take(4).forEach { feature ->
                    FeatureItemCard(
                        feature = feature,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}