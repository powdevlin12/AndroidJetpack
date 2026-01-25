package com.dattran.unitconverter.social.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dattran.unitconverter.R

data class NavigationItem(
    val title: String,
    val icon: Int,
    val route: String,
    val hasBadge: Boolean = false,
    val badgeCount: Int = 0
)

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            NavigationItem(
                title = "Trang chủ",
                icon = R.drawable.home,
                route = "home"
            ),
            NavigationItem(
                title = "Mua sắm",
                icon = R.drawable.cart,
                route = "category"
            ),
            NavigationItem(
                title = "Thông báo",
                icon = R.drawable.bell,
                route = "cart",
                hasBadge = true,
                badgeCount = 3
            ),
            NavigationItem(
                title = "Tài khoản",
                icon = R.drawable.account,
                route = "profile"
            )
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                clip = false
            ),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    item = item,
                    isSelected = selectedRoute == item.route,
                    onClick = { onItemSelected(item.route) }
                )
            }
        }
    }
}

@Composable
private fun NavigationBarItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background for selected item
//            if (isSelected) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(
//                            color = Color(0xFF1E88E5).copy(alpha = 0.1f),
//                            shape = RoundedCornerShape(12.dp)
//                        )
//                )
//            }

            // Icon with badge
            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.title,
                    tint = if (isSelected) Color(0xFFFFC62D) else Color(0xFF7E7E7E),
                    modifier = Modifier.size(24.dp)
                )

                // Badge
                if (item.hasBadge && item.badgeCount > 0) {
                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = (-4).dp)
                            .size(16.dp)
                            .background(
                                color = Color(0xFFFF3B30),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (item.badgeCount > 9) "9+" else item.badgeCount.toString(),
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        // Label
        Text(
            text = item.title,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) Color(0xFFFFC62D) else Color(0xFF7E7E7E)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomNavigationBar() {
    var selectedRoute by remember { mutableStateOf("home") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        BottomNavigationBar(
            selectedRoute = selectedRoute,
            onItemSelected = { selectedRoute = it },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

