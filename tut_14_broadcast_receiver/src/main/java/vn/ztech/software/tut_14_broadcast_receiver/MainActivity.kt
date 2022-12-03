package vn.ztech.software.tut_14_broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    lateinit var mbr: MBradCaseReceiver
    lateinit var localReceiver: LocalReceiver
    lateinit var btSend: Button
    private val CUSTOM_ACTION = BuildConfig.APPLICATION_ID + "BRC"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSend = findViewById(R.id.btSend)

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        mbr = MBradCaseReceiver()
        registerReceiver(mbr, intentFilter)

        localReceiver = LocalReceiver()
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, IntentFilter(CUSTOM_ACTION))

        btSend.setOnClickListener {
            sendCustomBroadcast()
        }
    }

    private fun sendCustomBroadcast() {
        val intent = Intent(CUSTOM_ACTION)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    class MBradCaseReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when(intent.action){
                    Intent.ACTION_POWER_CONNECTED -> {
                        Toast.makeText(context, "ACTION_POWER_CONNECTED", Toast.LENGTH_LONG).show()
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        Toast.makeText(context, "ACTION_POWER_DISCONNECTED", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    class LocalReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Toast.makeText(context, intent.action, Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDestroy() {
        unregisterReceiver(mbr)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver)
        super.onDestroy()
    }
}