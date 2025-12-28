package com.dattran.unitconverter.movie_project.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dattran.unitconverter.movie_project.constant.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCustom(
    value: String,
    onChange: (value: String) -> Unit,
    labelComposable: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = { it ->
            onChange(it)
        },
        label = labelComposable,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppColor.bgGray,   // Color when the text field is focused
            unfocusedContainerColor = AppColor.bgGray, // Color when unfocused
            focusedIndicatorColor = Color.Transparent, // Removes the bottom border when focused
            unfocusedIndicatorColor = Color.Transparent, // Removes the bottom border when unfocused
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = trailingIcon
    )
}

@Preview
@Composable
fun PreviewTFC() {
    TextFieldCustom(
        value = "",
        onChange = {},
        labelComposable = { Text("Your password", color = AppColor.textSecondary) },
        trailingIcon = {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Clear text",
                    tint = AppColor.textBlue,
                )
            }
        }

    )
}