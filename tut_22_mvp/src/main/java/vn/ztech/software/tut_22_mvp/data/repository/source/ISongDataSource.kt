package vn.ztech.software.tut_22_mvp.data.repository.source

import android.content.Context
import vn.ztech.software.tut_22_mvp.data.model.Song
import vn.ztech.software.tut_22_mvp.data.repository.IOnResultListener

interface ISongDataSource {
    fun getSongs(context: Context? = null, listener: IOnResultListener<MutableList<Song>>)
}