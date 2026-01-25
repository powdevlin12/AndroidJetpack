package com.dattran.unitconverter.social.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dattran.unitconverter.MainActivity
import com.dattran.unitconverter.R

class NotificationHelper(private val context: Context) {
    companion object {
        // ⭐ Channel IDs
        const val CHANNEL_ID_DEFAULT = "default_channel"
        const val CHANNEL_ID_IMPORTANT = "important_channel"
        const val CHANNEL_ID_MOVIE = "movie_channel"

        // ⭐ Notification IDs
        const val NOTIFICATION_ID_DEFAULT = 1001
        const val NOTIFICATION_ID_MOVIE = 1002
        const val NOTIFICATION_ID_DOWNLOAD = 1003
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        createNotificationChannels()
    }

    // ⭐ BƯỚC 1: Tạo Notification Channels (Android 8.0+)
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Channel 1: Default
            val defaultChannel = NotificationChannel(
                CHANNEL_ID_DEFAULT,
                "Default Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General app notifications"
                enableLights(true)
                enableVibration(true)
            }

            // Channel 2: Important
            val importantChannel = NotificationChannel(
                CHANNEL_ID_IMPORTANT,
                "Important Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important alerts and updates"
                enableLights(true)
                enableVibration(true)
            }

            // Channel 3: Movie Updates
            val movieChannel = NotificationChannel(
                CHANNEL_ID_MOVIE,
                "Movie Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "New movie releases and updates"
            }

            // Register channels
            notificationManager.createNotificationChannel(defaultChannel)
            notificationManager.createNotificationChannel(importantChannel)
            notificationManager.createNotificationChannel(movieChannel)
        }
    }

    // ⭐ TYPE 1: Simple Notification
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showSimpleNotification(
        title: String,
        message: String,
        channelId: String = CHANNEL_ID_DEFAULT,
        notificationId: Int = NOTIFICATION_ID_DEFAULT
    ) {
        // Intent khi click notification
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.bell) // Icon nhỏ
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Auto dismiss khi click
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Show notification
        notificationManager.notify(notificationId, notification)
    }

    // ⭐ TYPE 2: Big Text Notification (Text dài)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showBigTextNotification(
        title: String,
        message: String,
        bigText: String
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_DEFAULT)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(bigText)
                    .setBigContentTitle(title)
                    .setSummaryText("Movie App")
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(NOTIFICATION_ID_MOVIE, notification)
    }

    // ⭐ TYPE 3: Big Picture Notification (Có ảnh lớn)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showBigPictureNotification(
        title: String,
        message: String,
        imageResId: Int
    ) {
        val bitmap = BitmapFactory.decodeResource(context.resources, imageResId)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_MOVIE)
            .setSmallIcon(R.drawable.facebook)
            .setContentTitle(title)
            .setContentText(message)
            .setLargeIcon(bitmap) // Icon lớn bên trái
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
//                    .bigLargeIcon(null) // Ẩn large icon khi expand
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(NOTIFICATION_ID_MOVIE, notification)
    }

    // ⭐ Notification với Deep Link
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showDeepLinkNotification(
        title: String,
        message: String
    ) {
        // ⭐ FIX: Tạo Intent đúng cách cho deeplink
        val deepLinkIntent = Intent(context, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("loyaltyapp://main/register")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            deepLinkIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_MOVIE)
            .setSmallIcon(R.drawable.ic_splash_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(NOTIFICATION_ID_MOVIE, notification)
    }
}