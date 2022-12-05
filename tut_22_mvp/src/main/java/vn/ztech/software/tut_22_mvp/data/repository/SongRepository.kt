package vn.ztech.software.tut_22_mvp.data.repository

import android.content.Context
import vn.ztech.software.tut_22_mvp.data.model.Song
import vn.ztech.software.tut_22_mvp.data.repository.source.ISongDataSource

class SongRepository private constructor
    (val local: ISongDataSource, val remote: ISongDataSource): ISongDataSource{


    companion object{
        private var instane: SongRepository? = null
        fun getInstance(local: ISongDataSource, remote: ISongDataSource): SongRepository{
            synchronized(this){
                return instane ?: SongRepository(local, remote)
            }
        }
    }

    override fun getSongs(context: Context?, listener: IOnResultListener<MutableList<Song>>) {
        local.getSongs(context, listener)
    }
}