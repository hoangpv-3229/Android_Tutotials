package vn.ztech.software.tut_22_mvp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MusicControlReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getIntExtra("ACTION_MUSIC",-1)

        val intent = Intent(context, MusicPlayerService::class.java)
        intent.putExtra("ACTION_MUSIC", action)

        context?.startService(intent)
    }
}