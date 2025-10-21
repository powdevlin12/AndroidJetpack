package com.dattran.unitconverter.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.R

@Preview
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(0.25f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Login",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        )

        MyTextField(
            textFieldState = TextFieldState(),
            hint = "Email",
            leadingIcon = Icons.Outlined.Email,
            trailingIcon = Icons.Outlined.Check,
            modifier = Modifier.fillMaxWidth()
        )

        MyTextField(
            textFieldState = TextFieldState(),
            hint = "Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingText = "Forgot?",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login", fontSize = 17.sp, modifier = Modifier.padding(vertical = 8.dp))
        }

        Text(
            text = "Or, login with...",
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(0.5f)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            AuthOption(
                image = R.drawable.google
            )
            AuthOption(
                image = R.drawable.facebook
            )
            AuthOption(
                image = R.drawable.apple,
            )
        }

        Text(
            text = "Don't have account? Register",
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

    }
}