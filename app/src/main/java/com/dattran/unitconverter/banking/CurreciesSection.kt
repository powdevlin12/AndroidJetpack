package com.dattran.unitconverter.banking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowCircleUp
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CurrencyYen
import androidx.compose.material.icons.rounded.Euro
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmedapps.bankningappui.ui.theme.GreenEnd
import com.dattran.unitconverter.ui.theme.Size

val currencies = listOf(
    Currency(
        name = "USD",
        buy = 23.35f,
        sell = 23.25f,
        icon = Icons.Rounded.AttachMoney
    ),

    Currency(
        name = "EUR",
        buy = 13.35f,
        sell = 13.25f,
        icon = Icons.Rounded.Euro
    ),

    Currency(
        name = "YEN",
        buy = 26.35f,
        sell = 26.35f,
        icon = Icons.Rounded.CurrencyYen
    ),

    Currency(
        name = "USD",
        buy = 23.35f,
        sell = 23.25f,
        icon = Icons.Rounded.AttachMoney
    ),

    Currency(
        name = "EUR",
        buy = 63.35f,
        sell = 73.25f,
        icon = Icons.Rounded.Euro
    ),

    Currency(
        name = "YEN",
        buy = 16.35f,
        sell = 16.35f,
        icon = Icons.Rounded.CurrencyYen
    ),
)


@Preview
@Composable
fun CurrenciresSection() {
    val size = Size()
    val widthItem = size.width() / 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = "up",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Currencies",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(
                modifier = Modifier.width(widthItem),
                text = "Currency",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )


            Text(
                modifier = Modifier.width(widthItem),
                text = "Buy",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End
            )
            Text(
                modifier = Modifier.width(widthItem),
                text = "Sell",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(currencies.size) {
                CurrencyItem(index = it, width = widthItem)
            }
        }
    }

}

@Composable
fun CurrencyItem(index: Int, width: Dp) {
    val currency = currencies[index]
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(width)) {
            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(GreenEnd).padding(2.dp)){
                Icon(imageVector = currency.icon, contentDescription = "icon", tint = Color.White, modifier = Modifier.width(20.dp).height(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = currency.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Text(
            modifier = Modifier.width(width),
            text = "$ ${currency.buy}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
        Text(
            modifier = Modifier.width(width),
            text = "$ ${currency.sell}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
    }
}