package vn.ztech.software.tut_22_mvp.screen

import android.content.Context
import vn.ztech.software.tut_22_mvp.data.model.Song

class SongContract {
    interface View{
        fun onGetSongsSuccess(songs: MutableList<Song>)
        fun onError(e: java.lang.Exception)
    }

    interface Presenter{
        fun getSongs(context: Context)
    }
}