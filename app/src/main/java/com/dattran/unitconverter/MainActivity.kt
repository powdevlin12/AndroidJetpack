package com.dattran.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.banking.BottomNavigatioBar
import com.dattran.unitconverter.banking.CardsSection
import com.dattran.unitconverter.banking.CurrenciresSection
import com.dattran.unitconverter.banking.FinanceSection
import com.dattran.unitconverter.banking.WalletSection
import com.dattran.unitconverter.login.LoginScreen
import com.dattran.unitconverter.ui.theme.UnitConverterTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                SetBarColor(color = MaterialTheme.colorScheme.background)

                LoginScreen()
            }
        }
    }
}

@Composable
private fun SetBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color
        )
    }
}

@Preview
@Composable
private fun HomeScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigatioBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            WalletSection()
            Spacer(modifier = Modifier.height(16.dp))
            CardsSection()
            Spacer(modifier = Modifier.height(16.dp))
            FinanceSection()
            Spacer(modifier = Modifier.height(16.dp))
            CurrenciresSection()
        }
    }
}


@Composable
fun UnitConverter(innerPadding: PaddingValues) {
    var isExpandedInput by remember { mutableStateOf(false) }
    var isExpandedOutput by remember { mutableStateOf(false) }
    var valueInput by remember { mutableStateOf("") }
    var valueResult by remember { mutableStateOf("0") }
    var dvInput by remember { mutableStateOf("") }
    var dvOutput by remember { mutableStateOf("") }
    var iConvertRatio by remember { mutableFloatStateOf(1.0f) }
    var oConvertRatio by remember { mutableFloatStateOf(1.0f) }
    var isOpenAlert by remember { mutableStateOf(false) }

    fun handleChooseDv(dv: String, isDvInput: Boolean = true, radioConvert: Float) {
        if (isDvInput) {
            dvInput = dv
            isExpandedInput = false
            iConvertRatio = radioConvert
        } else {
            dvOutput = dv
            isExpandedOutput = false
            oConvertRatio = radioConvert
        }
    }

    fun handleConvert() {
        val ratio = iConvertRatio / oConvertRatio
        val result = (valueInput.toFloatOrNull() ?: 0.0f) * ratio
        valueResult = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", fontSize = 30.sp, color = Color.Red)
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.width(360.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.width(200.dp),
                value = valueInput,
                onValueChange = { valueInput = it },
                placeholder = { Text("Enter your value") }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(60.dp)
            ) {
                Button(onClick = { handleConvert() }) {
                    Text("Convert")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Box {
                Button(onClick = { isExpandedInput = !isExpandedInput }) {
                    Text(
                        if (dvInput == "") "Select" else dvInput,
                        modifier = Modifier.padding(12.dp, 6.dp)
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown unit")
                }
                DropdownMenu(
                    expanded = isExpandedInput,
                    onDismissRequest = { isExpandedInput = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Centimeter") },
                        onClick = { handleChooseDv("Centimeter", radioConvert = 1.0f) }
                    )
                    DropdownMenuItem(
                        text = { Text("Meter") },
                        onClick = { handleChooseDv("Meter", radioConvert = 100.0f) }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = { handleChooseDv("Feet", radioConvert = 30.48f) }
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box {
                Button(onClick = { isExpandedOutput = !isExpandedOutput }) {
                    Text(
                        if (dvOutput == "") "To" else dvOutput,
                        modifier = Modifier.padding(12.dp, 6.dp)
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown unit")
                }
                DropdownMenu(
                    expanded = isExpandedOutput,
                    onDismissRequest = { isExpandedOutput = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Centimeter") },
                        onClick = { handleChooseDv("Centimeter", false, 1.0f) },
                        leadingIcon = { Text("1.") }
                    )
                    DropdownMenuItem(
                        text = { Text("Meter") },
                        onClick = { handleChooseDv("Meter", false, 100.0f) },
                        leadingIcon = { Text("2.") }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = { handleChooseDv("Feet", false, 30.48f) },
                        leadingIcon = { Text("3.") }
                    )
                }
            }
        }

        Row(modifier = Modifier.padding(20.dp)) {
            Text("Result: $valueResult")
        }

        Box {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(16.dp)
                    .background(Color.Green, CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    buildAnnotatedString {
                        append("Welcome to ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.W900,
                                color = Color(0xFF4552B8)
                            )
                        ) {
                            append("Jetpack Compose Playground")
                        }
                    }
                )
            }
        }

        Row {
            Button(onClick = { isOpenAlert = true }) {
                Text("Open Alert")
            }

            if (isOpenAlert) {
                AlertDialog(
                    onDismissRequest = { isOpenAlert = false },
                    title = { Text(text = "Dialog Title") },
                    text = { Text("Here is a text ") },
                    confirmButton = {
                        Button(onClick = { isOpenAlert = false }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            onClick = { isOpenAlert = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
