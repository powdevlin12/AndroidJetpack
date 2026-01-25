package com.dattran.unitconverter.social.data.model

import androidx.compose.ui.graphics.Color

data class FeatureItem(
    val id: Int,
    val title: String,
    val icon: Int,
    val backgroundColor: Color,
    val hasNewBadge: Boolean = false
)

data class UserStats(
    val name: String,
    val barcode: String,
    val points: String,
    val gifts: Int
)

