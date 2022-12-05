package vn.ztech.software.tut_22_mvp.data.repository.source.local

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import vn.ztech.software.tut_22_mvp.data.model.Song
import vn.ztech.software.tut_22_mvp.data.repository.IOnResultListener
import vn.ztech.software.tut_22_mvp.data.repository.source.ISongDataSource

class SongLocalDataSource: ISongDataSource {

    override fun getSongs(context: Context?, listener: IOnResultListener<MutableList<Song>>) {
        try {
            val songs = mutableListOf<Song>()

            context?.let {
                val NAME_COLLUM = MediaStore.Audio.Media.TITLE
                val AUTHOR_COLLUM = MediaStore.Audio.Media.ARTIST
                val URI_COLLUM = MediaStore.Audio.Media.DATA

                val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf(NAME_COLLUM, AUTHOR_COLLUM, URI_COLLUM), null, null, NAME_COLLUM)
                while (cursor?.moveToNext()?:false){

                    val name = cursor?.getString(0)?:"null"
                    val author = cursor?.getString(1)?:"null"
                    val uri = cursor?.getString(2)

                    songs.add(Song(name,author, Uri.parse(uri)))
                    Log.d("MainActivity", uri.toString())
                }
            }

            listener.onSuccess(songs)
        }catch (e: java.lang.Exception){
            listener.onError(e)
        }

    }
    companion object {
        private var instance = null
        fun getInstance(): SongLocalDataSource{
            synchronized(this){
                return instance?:SongLocalDataSource()
            }
        }
    }
}