package ru.otus.webinar

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object Notifications {
    /**
     * Creates notification
     */
    fun buildNotification(context: Context, id: Int, text: String, intent: PendingIntent) {
        val builder = NotificationCompat.Builder(context, NOTIFICATIONS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(text)
            .setContentText(text)
            .setWhen(System.currentTimeMillis())
            .setContentIntent(intent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        if (notificationManager.areNotificationsEnabled()) {
            notificationManager.notify(id, builder.build())
        }
    }

    fun init(context: Context) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let {
            registerChannels(context, it)
        }
    }

    /**
     * Registers
     */
    private fun registerChannels(context: Context, manager: NotificationManager): NotificationManager = manager.apply {
        createNotificationChannel(createNotificationsChannel(context))
    }

    private fun createNotificationsChannel(context: Context): NotificationChannel = NotificationChannel(
        NOTIFICATIONS,
        context.getString(R.string.channel_notifications),
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        description = context.getString(R.string.channel_notifications_description)
    }

    private const val NOTIFICATIONS = "notifications"
}