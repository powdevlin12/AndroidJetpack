package com.dattran.unitconverter.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Size {
    @Composable
    fun height(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp.dp
    }

    @Composable
    fun width(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenWidthDp.dp
    }
}