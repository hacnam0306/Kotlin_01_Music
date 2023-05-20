package com.example.mymusic

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore.Audio.Media
import androidx.annotation.RequiresApi
import androidx.appcompat.resources.Compatibility.Api21Impl
import androidx.core.app.NotificationCompat


class MusicService :Service(){
    private var mediaPlayer: MediaPlayer?= null
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.getStringExtra(ACTION)){
            Constants.START ->{
                startForegroundInternal()
                playMedia()
                }
            Constants.STOP ->{
                stopForegroundInternal()
                stopMedia()
            }
        }
    return  START_NOT_STICKY;
    }

    private fun stopMedia() {
        mediaPlayer?.run {
            stop()
            release()
        }
        mediaPlayer = null
    }

    private fun playMedia(){
        mediaPlayer?.run {
            if(!isPlaying){
                start()
            }else{
                pause()
            }
            return
        }
    mediaPlayer = MediaPlayer.create(this,
    R.raw.wtfy
        ).apply {
            isLooping =true
        setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).build()
        )
    }
    }

private fun startForegroundInternal() {
    val channelId =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service", "My Background Service")
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            ""
        }

    val notificationBuilder = NotificationCompat.Builder(this, channelId )
    val notification = notificationBuilder.setOngoing(true)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setCategory(Notification.CATEGORY_SERVICE)
        .build()
    startForeground(101, notification)
}
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
    private fun stopForegroundInternal(){
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    companion object{
        const val ACTION = "action"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_id"
    }
}
