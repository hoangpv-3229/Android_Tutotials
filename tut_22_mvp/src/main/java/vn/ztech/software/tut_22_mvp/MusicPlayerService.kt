package vn.ztech.software.tut_22_mvp

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MusicPlayerService : Service() {
    companion object{
        val ACTION_CANCEL = 0
        val ACTION_PLAY = 1
        val ACTION_PAUSE = 2
        val ACTION_START = 3
    }
    var mediaPlayer: MediaPlayer? = null
    lateinit var currSong: Song
    var isPlaying = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras
        bundle?.let {
            val song = it.getParcelable<Song>("SONG")
            song?.let{
                currSong = it
                playMusic(it)
                startForeground(1, sendNotiMedia(it))
            }
        }

        val action = intent?.getIntExtra("ACTION_MUSIC", -1)
        handleAction(action, 1)

        return START_STICKY
    }


    private fun handleAction(action: Int?, startId: Int) {
        when(action){
            ACTION_CANCEL->{
                stopSelf()
            }
            ACTION_PLAY->{
                resumeMusic(startId)
            }
            ACTION_PAUSE->{
                pauseMusic(startId)
            }
            else->{
            }
        }
    }

    private fun resumeMusic(startId: Int) {
        if (mediaPlayer!=null && !isPlaying){
            mediaPlayer?.let {
                it.start()
                isPlaying = true
                startForeground(startId, sendNotiMedia(currSong))
                sendBroadcastToActivity(ACTION_PLAY)
            }
        }
    }

    private fun pauseMusic(startId: Int) {
        if (mediaPlayer!=null && isPlaying){
            mediaPlayer?.let {
                it.pause()
                isPlaying = false
                startForeground(startId, sendNotiMedia(currSong))
                sendBroadcastToActivity(ACTION_PAUSE)
            }
        }
    }

    private fun playMusic(song: Song) {
        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(applicationContext, song.uri)
        }
        mediaPlayer?.start()
        isPlaying = true
        sendBroadcastToActivity(ACTION_START)
    }
    private fun sendBroadcastToActivity(action: Int){
        val intent = Intent("ACTION_UPDATE_MUSIC")
        val bundle = Bundle()
        bundle.putParcelable("SONG", currSong)
        bundle.putInt("ACTION", action)
        bundle.putBoolean("IS_PLAYING", isPlaying)
        intent.putExtras(bundle)

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
    fun sendNotiMedia(data: Song): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val imgBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)
        val overrideLargeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)

        return NotificationCompat.Builder(applicationContext,"foreground_service_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(data.name)
            .setContentText(data.author)
            .setLargeIcon(imgBitmap)
            .addAction(if (isPlaying) R.drawable.ic_baseline_pause_circle_filled_24 else R.drawable.ic_baseline_play_circle_filled_24,
                if (isPlaying) "Pause" else "Play",
                if (isPlaying) getPendingIntentControlMusic(ACTION_PAUSE) else getPendingIntentControlMusic(ACTION_PLAY))
            .addAction(R.drawable.ic_baseline_cancel_24, "Cancel", getPendingIntentControlMusic(ACTION_CANCEL))
            .setOngoing(true)
//            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
//                .setShowActionsInCompactView(1))
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(imgBitmap).setBigContentTitle(data.name.uppercase()).bigLargeIcon(overrideLargeIcon))
            .setChannelId(MainActivity.CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSound(null)
            .build()
    }
    private fun getPendingIntentControlMusic(action: Int): PendingIntent {
        val intent = Intent(this, MusicControlReceiver::class.java)
        intent.putExtra("ACTION_MUSIC", action)
        return PendingIntent.getBroadcast(this, action, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }
    override fun onDestroy() {
        Log.d("xxx","destroy")
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        mediaPlayer?.let {
            mediaPlayer?.release()
            mediaPlayer = null
        }
        sendBroadcastToActivity(ACTION_CANCEL)
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}