package vn.ztech.software.tut_13_internet_connection

import android.content.Context
import android.hardware.input.InputManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.provider.FontsContractCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {
    lateinit var etSearch: EditText
    lateinit var btSearch: Button
    lateinit var tvResult: TextView
    lateinit var btSearchWithLoader: Button
    lateinit var tvResultLoader: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSearch = findViewById(R.id.etSearch)
        btSearch = findViewById(R.id.btSearch)
        tvResult = findViewById(R.id.tvResult)
        btSearchWithLoader = findViewById(R.id.btSearchWithLoader)
        tvResultLoader = findViewById(R.id.tvResultLoader)
        btSearch.setOnClickListener {
            search(etSearch.text.toString(), WeakReference(tvResult))
        }
        btSearchWithLoader.setOnClickListener {
            searchWithLoader(etSearch.text.toString())
        }
        if(supportLoaderManager.getLoader<String>(0) != null){
            supportLoaderManager.initLoader(0,null,this)
        }
    }

    private fun searchWithLoader(keyWord: String) {
        checkConditionAndSearch(keyWord){
            val bundle = Bundle()
            bundle.putString("KEY_WORD", keyWord)
            supportLoaderManager.restartLoader(0, bundle, this)
        }
    }

    private fun search(keyWord: String, tvResult: WeakReference<TextView>) {
        checkConditionAndSearch(keyWord) { SearchBookAsyncTask(tvResult).execute(keyWord) }
    }
    private fun checkConditionAndSearch(keyWord: String, logic: ()->Unit){
        if (canDoTheSearch(keyWord))
            logic()
        else
            updateUICannotDoTheSearch()

        hideSoftInput()
    }



    private fun canDoTheSearch(keyWord: String): Boolean {
        val conMa = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = conMa?.activeNetworkInfo
        return keyWord.isNotEmpty() && netInfo != null && netInfo.isConnected
    }
    private fun updateUICannotDoTheSearch() {
        if (etSearch.text.isEmpty())
            tvResult.text = "Search word is empty"
        else
            tvResult.text = "No internet"
    }
    private fun hideSoftInput() {
        val inputMa = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMa?.let {
            it.hideSoftInputFromWindow(btSearch.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    inner class SearchBookAsyncTask(val tvResult: WeakReference<TextView>): AsyncTask<String, Void, String>(){

        val  LOG = "SearchBookAsyncTask"

        override fun doInBackground(vararg params: String): String? {
            val keyWord = params[0]
            return BookUltils().fetchBookData(keyWord)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val book = getFirstBook(result)
            tvResult.get()?.text = " ${book.first}  ${book.second}"
        }

    }


    fun getFirstBook(data: String?): Pair<String, String> {
        if (data == null) return Pair("","")
        val LOG = "getFirstBook"
        try {
            val jobj = JSONObject(data)
            val items = jobj.getJSONArray("items")
            var title = ""
            var author = ""
            for (i in 0 until items.length()){
                val item = items.getJSONObject(i)
                val volume = item.getJSONObject("volumeInfo")
                try {
                    title = volume.getString("title")
                    author = volume.getString("authors")
                }catch (e: Exception){
                    Log.d(LOG, "Err:" + e.message.toString())
                }
                return Pair(title, author)
            }
        }catch (e: Exception){
            Log.d(LOG, e.message.toString())
        }
        return Pair("","")
    }



    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        var keyWord = ""
        args?.let {
            keyWord=args.getString("KEY_WORD")?:""
        }
        return SearchBookLoader(this, keyWord)
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        val book = getFirstBook(data)
        tvResultLoader.text = "${book.first} ${book.second}"
    }

    override fun onLoaderReset(loader: Loader<String>) {
    }
}