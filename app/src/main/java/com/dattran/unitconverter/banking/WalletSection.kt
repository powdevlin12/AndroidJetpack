package com.dattran.unitconverter.banking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//
//@Preview
//@Composable
//fun WalletSection() {
//    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically)  {
//        Column {
//            Text(text = "Wallet", fontSize = 17.sp, color = MaterialTheme.colorScheme.onBackground)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "$ 44.475", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
//        }
//        Box(modifier = Modifier.clip(RoundedCornerShape(15.dp)).background(MaterialTheme.colorScheme.secondaryContainer).clickable{}.padding(6.dp)) {
//            Icon(
//                imageVector = Icons.Rounded.Search, contentDescription = "Search",
//                tint = MaterialTheme.colorScheme.secondary,
//            )
//        }
//    }
//}
//














@Preview
@Composable
fun WalletSection() {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "Wallet", style = TextStyle(
                fontSize = 22.sp, color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.SemiBold
            ))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text="$ 44.475", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer))
        }
        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.secondaryContainer).padding(4.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.secondary)
        }
    }
}