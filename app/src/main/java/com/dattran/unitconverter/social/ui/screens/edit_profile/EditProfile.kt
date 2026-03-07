package com.dattran.unitconverter.social.ui.screens.edit_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.social.ui.components.ButtonCustom
import com.dattran.unitconverter.social.ui.screens.edit_profile.components.LabelInput

enum class FormField {
    NAME,
    BIO,
    WEBSITE,
    LOCATION,
    EMAIL,
}

@Composable
fun EditProfileScren() {
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    fun updateField(field: FormField, value: String) {
        when (field) {
            FormField.NAME -> name = value
            FormField.BIO -> bio = value
            FormField.WEBSITE -> website = value
            FormField.LOCATION -> location = value
            FormField.EMAIL -> email = value
        }
    }

    val scrollState = rememberScrollState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {}) {
                    Text("Cancel", fontSize = 16.sp, color = Color(0xFF64748B))
                }
                Text(
                    "Edit Profile",
                    fontSize = 18.sp,
                    color = Color(0xFF0F172A),
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.width(70.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box() {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp),
                        tint = Color.LightGray
                    )

                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .width(32.dp)
                            .height(32.dp)
                            .offset(x = (-4).dp, y = (-4).dp),
                        tint = Color(0xFF257BF4)
                    )
                }
            }
            TextButton(onClick = {}) {
                Text(
                    "Change Profile Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF257BF4),
                    textAlign = TextAlign.Center
                )
            }

            InputEditProfile(
                name = name,
                bio = bio,
                website = website,
                location = location,
                email = email,
                onFieldChange = ::updateField
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonCustom(
                text = "Save changes",
                onClick = {},
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun InputEditProfile(
    name: String,
    bio: String,
    website: String,
    location: String,
    email: String,
    onFieldChange: (FormField, String) -> Unit
) {
    val fields = listOf(
        Triple(FormField.NAME, "Name", name),
        Triple(FormField.BIO, "Bio", bio),
        Triple(FormField.WEBSITE, "Website", website),
        Triple(FormField.LOCATION, "Location", location),
        Triple(FormField.EMAIL, "Email", email),
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        fields.forEach { (field, label, value) ->
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                LabelInput(label)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = value,
                onValueChange = { onFieldChange(field, it) },
                placeholder = { Text("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(48),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFF8FAFC),
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    cursorColor = Color(0xFF257BF4)
                ),
                singleLine = true
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    EditProfileScren()
}