package com.dattran.unitconverter.social.ui.screens.register

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.social.constant.AppColor
import com.dattran.unitconverter.social.data.model.UserRegisterBody
import com.dattran.unitconverter.social.ui.components.AlertCustom
import com.dattran.unitconverter.social.ui.components.ButtonCustom
import com.dattran.unitconverter.social.ui.components.TextFieldCustom

enum class FormField {
    NAME,
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD
}


@Composable
fun Register(
    viewModel: RegisterViewModel = RegisterViewModel(),
) {
    var name by remember { mutableStateOf("TranThuDat") }
    var email by remember { mutableStateOf("thudat@gmail.com") }
    var password by remember { mutableStateOf("dat123") }
    var confirmPassword by remember { mutableStateOf("dat123") }

    val uiState by viewModel.uiState.collectAsState()


    fun updateField(field: FormField, value: String) {
        when (field) {
            FormField.NAME -> name = value
            FormField.EMAIL -> email = value
            FormField.PASSWORD -> password = value
            FormField.CONFIRM_PASSWORD -> confirmPassword = value
        }
    }

    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    "Đăng ký", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("Đăng nhập để sử dụng dịch vụ")
                Spacer(modifier = Modifier.height(30.dp))
                TextFieldCustom(
                    value = name,
                    onChange = { it ->
                        updateField(FormField.NAME, it)
                    },
                    labelComposable = { LabelTextField("Nhập tên của bạn") },
                )
                Spacer(modifier = Modifier.height(20.dp))
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
                TextFieldCustom(
                    value = confirmPassword,
                    onChange = { it ->
                        updateField(FormField.CONFIRM_PASSWORD, it)
                    },
                    labelComposable = { LabelTextField("Nhập lại mật khẩu của bạn") },
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonCustom(
                    text = "Đăng ký",
                    onClick = {
                        viewModel.handleRegister(
                            UserRegisterBody(
                                name = name,
                                email = email,
                                password = password,
                                confirm_password = confirmPassword
                            )
                        )
                    },
                    isLoading = uiState.isLoading
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Bạn đã có tài khoản? ",
                        style = TextStyle(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        "Đăng nhập ngay",
                        style = TextStyle(
                            color = AppColor.textBlue,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.clickable {
                            // Your action here (e.g., show a Toast, navigate)
                            println("Text clicked!")
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

@Composable
fun LabelTextField(label: String) {
    Text(label, color = AppColor.textSecondary)
}


@Preview
@Composable
fun Preview() {
    Register()
}