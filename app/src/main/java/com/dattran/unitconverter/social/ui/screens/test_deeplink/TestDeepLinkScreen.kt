package com.dattran.unitconverter.social.ui.screens.test_deeplink

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dattran.unitconverter.social.utils.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestDeepLinkScreen() {
    val context = LocalContext.current
    val notificationHelper = remember { NotificationHelper(context) }

    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    // ⭐ Launcher để request permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test DeepLink Notification") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🔔 Test DeepLink Notification",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Bấm vào nút bên dưới để gửi notification với deeplink đến màn hình Register",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ⭐ Check permission status
            if (hasNotificationPermission) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "✅ Permission OK",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Bạn có thể gửi notification",
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "⚠️ Cần Permission",
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            onClick = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Request Permission")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ⭐ Button gửi deeplink notification
            Button(
                onClick = {
                    if (hasNotificationPermission) {
                        notificationHelper.showDeepLinkNotification(
                            title = "🎬 Deeplink Test",
                            message = "Bấm vào đây để đi tới màn hình Register!"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = hasNotificationPermission
            ) {
                Text(
                    text = "📲 Gửi DeepLink Notification",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ⭐ Hướng dẫn test
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "📝 Cách test:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1. Bấm nút 'Gửi DeepLink Notification'")
                    Text("2. Notification sẽ xuất hiện ở thanh thông báo")
                    Text("3. Bấm vào notification")
                    Text("4. App sẽ mở và chuyển đến màn hình Register")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "🔗 DeepLink: loyaltyapp://main/register",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ⭐ Other test buttons
            OutlinedButton(
                onClick = {
                    if (hasNotificationPermission) {
                        notificationHelper.showSimpleNotification(
                            title = "Simple Notification",
                            message = "Đây là notification thông thường (không có deeplink)"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Test Simple Notification")
            }
        }
    }
}

