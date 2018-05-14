package com.j.ming.duttrade.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.j.ming.duttrade.App

/**
 * Created by sunny on 18-1-30.
 */

class MyNotificationUtil private constructor(val context: Context, channelId: String = App.APP_NAME) {

    private var notification: Notification? = null
    private var notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private var builder = NotificationCompat.Builder(context, channelId)

    companion object {
        private var default_notifyId = 0
        fun newInstance(context: Context): MyNotificationUtil {
            return MyNotificationUtil(context)
        }

        @Synchronized
        fun generateNotifyId(): Int {
            default_notifyId = (default_notifyId + 1) % 100
            return default_notifyId
        }

        fun createChannelForOreo(context: Context, channelId: String, channelName: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                channel.enableLights(true)
                channel.setShowBadge(true)
                channel.lightColor = Color.GREEN
                channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun notify(notification: Notification, notificationId: Int){
        notificationManager.notify(notificationId, notification)
    }

    fun cancel(notificationId: Int){
        notificationManager.cancel(notificationId)
    }

    fun show(notificationId: Int = generateNotifyId()): Int {
        notification = builder.build()
        notificationManager.notify(notificationId, notification)
        return notificationId
    }
}