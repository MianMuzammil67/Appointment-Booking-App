package com.example.appointmentbookingapp.util

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.presentation.MainActivity

class AppointmentReminderReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val appointmentId = intent.getStringExtra("appointmentId") ?: return

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("appointmentId", appointmentId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "appointments_channel")
            .setSmallIcon(R.drawable.logo) // Your app icon here
            .setContentTitle("Upcoming Appointment")
            .setContentText("Your video call will start soon.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(appointmentId.hashCode(), notification)
    }
}
