package vn.ztech.software.tut_13_internet_connection

import android.content.Context
import androidx.loader.content.AsyncTaskLoader

class SearchBookLoader(context: Context, val keyWord: String): AsyncTaskLoader<String>(context){
    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }
    override fun loadInBackground(): String? {
        return BookUltils().fetchBookData(keyWord = keyWord)
    }

}