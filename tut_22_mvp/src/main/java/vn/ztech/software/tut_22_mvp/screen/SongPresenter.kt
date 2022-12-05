package vn.ztech.software.tut_22_mvp.screen

import android.content.Context
import vn.ztech.software.tut_22_mvp.data.model.Song
import vn.ztech.software.tut_22_mvp.data.repository.IOnResultListener
import vn.ztech.software.tut_22_mvp.data.repository.SongRepository
import java.lang.Exception

class SongPresenter internal constructor(val songRepository: SongRepository?, val view: SongContract.View?): SongContract.Presenter{
    override fun getSongs(context: Context) {
        songRepository?.let {
            it.getSongs(context, object : IOnResultListener<MutableList<Song>>{
                override fun onSuccess(data: MutableList<Song>) {
                    view?.let { it.onGetSongsSuccess(data) }
                }

                override fun onError(e: Exception) {
                    view?.let { it.onError(e) }
                }

            })
        }
    }
}