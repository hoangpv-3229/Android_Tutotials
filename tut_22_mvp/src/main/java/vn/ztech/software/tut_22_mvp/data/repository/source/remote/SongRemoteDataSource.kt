package vn.ztech.software.tut_22_mvp.data.repository.source.remote

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import vn.ztech.software.tut_22_mvp.data.model.Song
import vn.ztech.software.tut_22_mvp.data.repository.IOnResultListener
import vn.ztech.software.tut_22_mvp.data.repository.source.ISongDataSource

class SongRemoteDataSource: ISongDataSource {

    override fun getSongs(context: Context?, listener: IOnResultListener<MutableList<Song>>) {
        // not implemented yet
    }
    companion object {
        private var instance = null
        fun getInstance(): SongRemoteDataSource{
            synchronized(this){
                return instance?:SongRemoteDataSource()
            }
        }
    }
}