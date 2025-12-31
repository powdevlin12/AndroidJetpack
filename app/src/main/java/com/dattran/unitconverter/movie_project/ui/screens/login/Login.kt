package com.dattran.unitconverter.movie_project.ui.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dattran.unitconverter.movie_project.constant.AppColor
import com.dattran.unitconverter.movie_project.data.model.UserLoginBody
import com.dattran.unitconverter.movie_project.data.model.UserRegisterBody
import com.dattran.unitconverter.movie_project.navigation.Screen
import com.dattran.unitconverter.movie_project.ui.components.AlertCustom
import com.dattran.unitconverter.movie_project.ui.components.ButtonCustom
import com.dattran.unitconverter.movie_project.ui.components.TextFieldCustom
import com.dattran.unitconverter.movie_project.ui.screens.register.LabelTextField
import com.dattran.unitconverter.movie_project.ui.screens.register.RegisterViewModel

enum class FormField {
    EMAIL,
    PASSWORD,
}

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
) {
    var email by remember { mutableStateOf("thudat@gmail.com") }
    var password by remember { mutableStateOf("!Thudat123") }

    val uiState by viewModel.uiState.collectAsState()

    fun updateField(field: FormField, value: String) {
        when (field) {
            FormField.EMAIL -> email = value
            FormField.PASSWORD -> password = value
        }
    }
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Đăng nhập", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("Đăng nhập để sử dụng dịch vụ")
                Spacer(modifier = Modifier.height(30.dp))
                TextFieldCustom(
                    value = email,
                    onChange = { it ->
                        updateField(FormField.EMAIL, it)
                    },
                    labelComposable = { LabelTextField("Nhập email của bạn") },
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextFieldCustom(
                    value = password,
                    onChange = { it ->
                        updateField(FormField.PASSWORD, it)
                    },
                    labelComposable = { LabelTextField("Nhập mật khẩu của bạn") },
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonCustom(
                    text = "Đăng nhập",
                    onClick = {
                        viewModel.handleLogin(
                            userForm = UserLoginBody(
                                email = email,
                                password = password,
                            ),
                            navController
                        )
                    },
                    isLoading = uiState.isLoading
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Bạn đã chưa tài khoản? ",
                        style = TextStyle(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        "Đăng ký ngay",
                        style = TextStyle(
                            color = AppColor.textBlue,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.clickable {
                            // Your action here (e.g., show a Toast, navigate)
                            navController.navigate(Screen.Register.route)
                        })
                }
                if (uiState.errorMsg.isNotEmpty()) {
                    AlertCustom(
                        title = "Thông báo",
                        message = uiState.errorMsg,
                        onlyOk = true,
                        handleConfirm = {
                            viewModel.handleSetErrorMsg("")
                        })
                }
            }
        }
    }
}