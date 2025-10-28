package com.dattran.unitconverter.uitabpager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.dattran.unitconverter.banking.WalletSection
import com.dattran.unitconverter.login.LoginScreen
import com.dattran.unitconverter.banking.BankingScreen
import kotlinx.coroutines.launch

data class TabItem(
    val text: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
)

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text("HomeScreen")
    }
}

@Composable
fun SettingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Text("SettingScreen")
    }
}


@Composable
fun PersonalScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text("PersonalScreen")
    }
}

@Composable
fun TabNavigation() {
    var tabIndex by remember {
        mutableStateOf(0)
    }

    var tabs = listOf(
        TabItem(text = "Home", icon = Icons.Default.Home) {
            BankingScreen()
        },
        TabItem(text = "Setting", icon = Icons.Default.Settings) {
            SettingScreen()
        },
        TabItem(text = "Personal", icon = Icons.Default.AccountCircle) {
            LoginScreen()
        },
    )

    var pagerState = rememberPagerState(
        pageCount = { tabs.size }
    )

    var coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    text = { Text(text = tabItem.text) },
                    icon = { Icon(tabItem.icon, contentDescription = null) },
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            tabIndex = index
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }

        HorizontalPager(state = pagerState) {
            tabs[it].screen()
        }
    }
}