package com.dattran.unitconverter.social.ui.components

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dattran.unitconverter.social.utils.NotificationHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TestNotification() {
    val context = LocalContext.current
    val notificationHelper = remember { NotificationHelper(context) }

    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }

    if (notificationPermissionState != null && !notificationPermissionState.status.isGranted) {
        Button(
            onClick = { notificationPermissionState.launchPermissionRequest() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Request Notification Permission")
        }
    }

    // ⭐ Simple Notification
    Button(
        onClick = {
            notificationHelper.showSimpleNotification(
                title = "Test notification",
                message = "Test notification message"
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show Simple Notification")
    }

    // ⭐ Big Text Notification
    Button(
        onClick = {
            notificationHelper.showBigTextNotification(
                title = "Movie Review",
                message = "The Avengers receive mixed reviews...",
                bigText = "The Avengers receive mixed reviews from critics. " +
                        "While the action sequences are spectacular and the ensemble " +
                        "cast delivers strong performances, some critics feel the plot " +
                        "is overly complex and the runtime is too long. Overall, it's " +
                        "a must-watch for Marvel fans."
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show Big Text Notification")
    }

    // ⭐ Big Picture Notification
    Button(
        onClick = {
            notificationHelper.showBigPictureNotification(
                title = "New Poster Released",
                message = "Check out the new Avengers poster!",
                imageResId = com.dattran.unitconverter.R.drawable.ic_splash_logo
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show Big Picture Notification")
    }

    // ⭐ Big Picture Notification
    Button(
        onClick = {
            notificationHelper.showDeepLinkNotification(
                title = "Notification with Deep Link",
                message = "Notification with Deep Link message",
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show Deep Link Notification")
    }
}