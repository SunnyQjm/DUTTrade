package com.j.ming.duttrade.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.j.ming.duttrade.R

/**
 * Created by sunny on 18-2-2.
 */

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

class PublishNotificationFactoryImpl : PublishNotification {
    private val channelId = "dut_trade"
    private val channelName = "dut_trade"
    private val map = mutableMapOf<PublishNotificationInfo, NotificationCompat.Builder>()


    override fun build(context: Context, publishNotificationInfo: PublishNotificationInfo): Notification? {
        createChannelForOreo(context, channelId, channelName)
        val builder = get(publishNotificationInfo, context)
        builder.setContentText("${publishNotificationInfo.curIndex}/${publishNotificationInfo.total}")
        builder.setProgress(100, publishNotificationInfo.totalPercent, publishNotificationInfo.indeterminate)
        return builder.build()
    }

    private fun get(publishNotificationInfo: PublishNotificationInfo, context: Context): NotificationCompat.Builder {

        var builder = map[publishNotificationInfo]
        if (builder == null) {
            builder = createNotificationBuilder(context)
            map[publishNotificationInfo] = builder
        }
        return builder
    }

    private fun createNotificationBuilder(context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("正在上传")
                .setAutoCancel(true)
//                .intentMain(context)            //点击以后跳转到上传下载记录界面
    }

}