package com.emirim.lifecurveitshow.Kotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){

    //메세지가 온 경우 호출됨
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage?.notification?.let{
            Log.d(TAG,"Message Notification Body: ${it.body}")
            sendNotification(it.body ?: "message")
        }
    }

    override fun onNewToken(token: String) {
       Log.d(TAG,"Refreshed token: $token")
        sendRegistrationToServer(token)
    }
    private fun sendRegistrationToServer(token: String?){

    }
    private fun sendNotification(messageBody: String){
        val intent= Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent=PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId="News"
        val defaultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder= NotificationCompat.Builder(this, channelId)
            .setContentTitle("LifeCurve 메세지")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificatinManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel=NotificationChannel(channelId,"Chanel human readable title",NotificationManager.IMPORTANCE_DEFAULT)
            notificatinManager.createNotificationChannel(channel)
        }
        notificatinManager.notify(0, notificationBuilder.build())
    }
    companion object{
        private const val TAG="MyFirebaseMsgService"
    }
}