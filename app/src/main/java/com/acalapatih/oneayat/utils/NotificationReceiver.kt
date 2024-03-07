package com.acalapatih.oneayat.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.acalapatih.oneayat.R
import com.acalapatih.oneayat.ui.hafalanquran.activity.HafalanQuranActivity

class NotificationReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val repeatingIntent = Intent(context, HafalanQuranActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, 0, repeatingIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, "Notification")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Sudahkah Kamu Setor Hafalan Hari Ini?")
            .setContentText("Lanjutkan hafalan Al-Qur'an mu minimal satu hari satu ayat, Hamasahh!!")
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }
}