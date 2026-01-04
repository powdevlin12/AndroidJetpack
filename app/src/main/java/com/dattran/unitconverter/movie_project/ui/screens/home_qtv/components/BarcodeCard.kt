package com.dattran.unitconverter.movie_project.ui.screens.home_qtv.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PreviewBarcodeCard() {
    BarcodeCard(barcode = "1088", modifier = Modifier.padding(16.dp))
}

@Composable
fun BarcodeCard(
    barcode: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFfffdfa)),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Đưa mã cho nhân viên để tích, sử dụng điểm",
                fontSize = 14.sp,
                color = Color(0xFF222222),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Barcode visualization - more realistic
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                // Generate more realistic barcode pattern
                Row(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val pattern = listOf(
                        1,
                        2,
                        2,
                        3,
                        2,
                        4,
                        1,
                        2,
                        6,
                        2,
                        2,
                        1,
                        1,
                        2,
                        6,
                        2,
                        2,
                        1,
                        2,
                        4,
                        1,
                        2,
                        1,
                        5,
                        2,
                        1,
                        1,
                        7,
                        1,
                        2,
                        1,
                        3,
                        2,
                        7,
                        2,
                        1,
                        2,
                        3,
                        1,
                        2,
                        6,
                        3,
                        2,
                        1,
                        3,
                        2,
                        6,
                        2,
                        3,
                        1,2,
                        1,
                        3,
                        2,
                        1,
                        3,
                        2,
                        1,
                        2,
                        3,
                        12,
                        1,
                        3,
                        2,
                        1,
                        3,
                        2,
                        1,
                        2,
                        3,
                        1
                    )
                    pattern.forEach { width ->
                        Box(
                            modifier = Modifier
                                .width(width.dp)
                                .height(43.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }

            Text(
                text = barcode,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                letterSpacing = 2.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
