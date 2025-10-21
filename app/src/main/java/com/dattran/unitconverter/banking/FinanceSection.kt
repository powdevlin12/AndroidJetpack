package com.dattran.unitconverter.banking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedapps.bankningappui.ui.theme.BlueStart
import com.ahmedapps.bankningappui.ui.theme.GreenStart
import com.ahmedapps.bankningappui.ui.theme.PurpleStart

val listFinances = listOf(
    Finance(
        icon = Icons.Rounded.Wallet,
        title = "My Wallet",
        background = BlueStart
    ),
    Finance(
        icon = Icons.Rounded.Analytics,
        title = "Finance Analysis",
        background = PurpleStart
    ),
    Finance(
        icon = Icons.Rounded.MonetizationOn,
        title = "My Transactions",
        background = GreenStart
    ),
    Finance(
        icon = Icons.Rounded.Analytics,
        title = "Finance Analysis",
        background = PurpleStart
    ),
)

@Preview
@Composable
fun FinanceSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Finance",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow {
            items(listFinances.size) { index ->
                FinanceItem(index)
            }
        }
    }
}

@Composable
fun FinanceItem(index: Int) {
    val finace = listFinances[index]
    var lastItemPaddingEnd = 0.dp
    if (index == cards.size - 1) {
        lastItemPaddingEnd - 16.dp
    }
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(100.dp)
            .padding(start = 16.dp, end = lastItemPaddingEnd)
            .clip(
                RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(12.dp)
            .fillMaxSize()) {
            Icon(
                imageVector = finace.icon, contentDescription = finace.title,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                   ,
                tint = finace.background
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = finace.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme
                .colorScheme.onSecondaryContainer)
        }
    }
}