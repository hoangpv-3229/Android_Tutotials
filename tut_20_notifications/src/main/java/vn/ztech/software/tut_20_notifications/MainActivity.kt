package vn.ztech.software.tut_20_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    lateinit var btNotify: Button
    lateinit var btCancel: Button
    lateinit var btUpdate: Button

    val CHANNEL_ID = "TEST_CHANNEL"
    val NOTI_ID = 1
    val ACTION_UPDATE_NOTI = BuildConfig.APPLICATION_ID + "ACTION_UPDATE_NOTI"
    lateinit var notiMa: NotificationManager
    lateinit var updateBRC: UpdateBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btNotify = findViewById(R.id.btNotify)
        btNotify.setOnClickListener {
            sendNoti()
        }

        btCancel = findViewById(R.id.btCancel)
        btCancel.setOnClickListener {
            cancelNoti()
        }

        btUpdate = findViewById(R.id.btUpdate)
        btUpdate.setOnClickListener {
            updateNoti()
        }
        Log.d("NOTIFICATION", "oncreate")
        updateBRC = UpdateBroadcastReceiver()
        registerReceiver(updateBRC, IntentFilter(ACTION_UPDATE_NOTI))
    }

    private fun updateNoti() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        val buidler = getNotiBuilder().setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("THis is Big picture style"))
        createNotificationChannel()
        notiMa.notify(NOTI_ID, buidler.build())
    }

    private fun cancelNoti() {
        notiMa.cancel(NOTI_ID)
    }

    private fun sendNoti() {
        createNotificationChannel()
        val builder = getNotiBuilder()

        val intentActionUpdate = Intent(ACTION_UPDATE_NOTI)
        val pendingIntent = PendingIntent.getBroadcast(this, NOTI_ID, intentActionUpdate, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT )
        builder.addAction(R.drawable.ic_baseline_system_update_alt_24, "This is action update", pendingIntent)

        notiMa.notify(NOTI_ID, builder.build())
    }

    private fun getNotiBuilder(): NotificationCompat.Builder{
        val pendingIntent = getPendingIntent()
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
        notificationBuilder.setContentTitle("setContentTitle")
            .setContentText("setContentText")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        return notificationBuilder
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, NOTI_ID, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    private fun createNotificationChannel() {
        if(!::notiMa.isInitialized)
            notiMa = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val notiChannel = NotificationChannel(CHANNEL_ID, "This is a test channel name", NotificationManager.IMPORTANCE_HIGH)
            notiChannel.enableLights(true)
            notiChannel.lightColor = Color.RED
            notiChannel.enableVibration(true)
            notiChannel.description = "This is the description of this notification channel"


            notiMa.createNotificationChannel(notiChannel)
        }
    }

    inner class UpdateBroadcastReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (it.action == ACTION_UPDATE_NOTI){
                    updateNoti()
                }
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(updateBRC)
        super.onDestroy()
    }
}