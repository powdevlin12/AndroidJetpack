package com.dattran.unitconverter.movie_project.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AlertCustom(
    title: String,
    message: String,
    onDismiss: () -> Unit = {},
    handleConfirm: () -> Unit,
    handleCancel: () -> Unit
) {
    // Implement your custom alert dialog here
    AlertDialog(
        onDismissRequest = {
            onDismiss
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick =
                    handleConfirm
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(
                onClick = handleCancel
            ) {
                Text("Huỷ")
            }
        }
    )
}