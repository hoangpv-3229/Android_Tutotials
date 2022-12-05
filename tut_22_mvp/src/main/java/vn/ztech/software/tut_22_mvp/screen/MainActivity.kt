package vn.ztech.software.tut_22_mvp.screen

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.ztech.software.tut_22_mvp.service.MusicPlayerService
import vn.ztech.software.tut_22_mvp.R
import vn.ztech.software.tut_22_mvp.data.model.Song
import vn.ztech.software.tut_22_mvp.data.repository.SongRepository
import vn.ztech.software.tut_22_mvp.data.repository.source.local.SongLocalDataSource
import vn.ztech.software.tut_22_mvp.data.repository.source.remote.SongRemoteDataSource
import vn.ztech.software.tut_22_mvp.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity(), SongAdapter.OnClickListener, SongContract.View {
    lateinit var recycler: RecyclerView
    companion object{
        val CHANNEL_ID = "TEST_CHANNEL"
    }
    var isPlaying = false
    lateinit var binding: ActivityMainBinding
    private lateinit var songPresenter: SongPresenter
    val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras
            bundle?.let {

                val song = it.getParcelable<Song>("SONG")
                isPlaying = it.getBoolean("IS_PLAYING")
                val action = it.getInt("ACTION")
                song?.let {
                    handleAction(action, song)
                }
            }
        }

    }

    private fun handleAction(action: Int, song: Song) {

        when(action){
            MusicPlayerService.ACTION_START ->{
                binding.layoutMusic.root.visibility = View.VISIBLE
                binding.layoutMusic.ivSong.setImageResource(R.drawable.ic_launcher_foreground)
                binding.layoutMusic.tvName.text = song.name
                binding.layoutMusic.tvSinger.text = song.author
                binding.layoutMusic.btPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)

                binding.layoutMusic.btPlayOrPause.setOnClickListener {
                    sendIntentToService(if (isPlaying) MusicPlayerService.ACTION_PAUSE else MusicPlayerService.ACTION_PLAY)
                }
                binding.layoutMusic.btCancel.setOnClickListener {
                    sendIntentToService(MusicPlayerService.ACTION_CANCEL)
                }
            }
            MusicPlayerService.ACTION_PLAY ->{
                if (isPlaying){
                    binding.layoutMusic.btPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
                }
            }
            MusicPlayerService.ACTION_PAUSE ->{
                if (!isPlaying){
                    binding.layoutMusic.btPlayOrPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24)
                }
            }
            MusicPlayerService.ACTION_CANCEL ->{
                binding.layoutMusic.root.visibility = View.GONE
            }
        }

    }
    private fun sendIntentToService(action: Int) {
        val intent = Intent(this, MusicPlayerService::class.java)
        intent.putExtra("ACTION_MUSIC", action)
        startService(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter("ACTION_UPDATE_MUSIC"))

        recycler = findViewById(R.id.recycler)
        createNotificationChannel()

        if (isPermissionGranted()){
            getSongs()
        }
    }
    private fun getSongs(){
        songPresenter = SongPresenter(
            SongRepository.getInstance(
                SongLocalDataSource.getInstance(),
                SongRemoteDataSource.getInstance()
            ),
            this)
        songPresenter.getSongs(this)
    }
    private fun showSongs(songs: MutableList<Song>){
        val adapter = SongAdapter(songs, this)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler.adapter = adapter
    }

    private fun isPermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Read Contacts permission")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage("Please enable access to contacts.")
                    builder.setOnDismissListener(DialogInterface.OnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ), 1000
                        )
                    })
                    builder.show()
                    return false
                }else{
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 1000
                    )
                    return false
                }
            }else return true
        } else {
            return true
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000->{
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getSongs()
                }else{
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    override fun onSongClicked(song: Song) {
        val intent = Intent(this, MusicPlayerService::class.java)
        val bundle = Bundle()
        bundle.putParcelable("SONG",song)
        intent.putExtras(bundle)
        startService(intent)
    }
    private fun createNotificationChannel() {
        val  notiMa = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val notiChannel = NotificationChannel(CHANNEL_ID, "This is a test channel name", NotificationManager.IMPORTANCE_LOW)
            notiChannel.enableLights(true)
            notiChannel.lightColor = Color.RED
            notiChannel.description = "This is the description of this notification channel"
            notiChannel.setSound(null, null)


            notiMa.createNotificationChannel(notiChannel)
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun onGetSongsSuccess(songs: MutableList<Song>) {
        showSongs(songs)
    }

    override fun onError(e: Exception) {
        AlertDialog.Builder(this)
            .setMessage("Error: ${e.message.toString()}")
            .setCancelable(true)
            .setPositiveButton("Oke", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                }
            })
    }
}