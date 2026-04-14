package com.example.appointmentbookingapp.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.appointmentbookingapp.domain.model.Appointment

class LocalNotificationManager(private val context: Context) {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleAppointmentNotification(appointment: Appointment) {
        val appointmentId = appointment.appointmentId
        val triggerTime = appointment.appointmentDate?.time?.minus(5 * 60 * 1000) ?: return // 5 min early

        val intent = Intent(context, AppointmentReminderReceiver::class.java).apply {
            putExtra("appointmentId", appointmentId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            appointmentId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}
