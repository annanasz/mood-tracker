package hu.bme.aut.android.mood

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationReceiver :BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
       val notificatinManager:NotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val repeatingIntent = Intent(context, MainActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
               "mychannelid",
                "channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificatinManager.createNotificationChannel(channel)
        }

        val pendingIntent = PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context,"mychannelid")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.awesome_cow)
            .setContentTitle("MOOO")
            .setContentText("Ne felejtsd el hozzáadni a napi bejegyzésed!")
            .setAutoCancel(true)
        notificatinManager.notify(100,builder.build())

    }
}