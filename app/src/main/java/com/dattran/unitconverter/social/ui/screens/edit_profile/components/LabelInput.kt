package com.dattran.unitconverter.social.ui.screens.edit_profile.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LabelInput(label : String) {
    Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF0F172A))
}

@Preview
@Composable
fun PreviewLabelInput() {
    LabelInput("Name")
}