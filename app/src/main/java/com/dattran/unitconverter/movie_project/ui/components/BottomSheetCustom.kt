package com.dattran.unitconverter.movie_project.ui.components

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCustom(
    title: String,
    onDismiss: () -> Unit,
    onSetShowSheet: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onSetShowSheet(false) },
        containerColor = androidx.compose.ui.graphics.Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = androidx.compose.ui.graphics.Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color(0xFF1F2933)
                )
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    modifier = Modifier.offset(y = (-2).dp).clickable(
                        onClick = onDismiss
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}