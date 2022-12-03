package vn.ztech.software.tut_13_internet_connection

import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class BookUltils {
    fun fetchBookData(keyWord: String): String?{
        // Base URL for Books API.
        val BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?"

        // Parameter for the search string.
        val QUERY_PARAM = "q"

        // Parameter that limits search results.
        val MAX_RESULTS = "maxResults"

        // Parameter to filter by print type.
        val PRINT_TYPE = "printType"
        Thread.sleep(1000)

        var httpURLConnection: HttpURLConnection? = null
        lateinit var bufferedReader : BufferedReader
        var stringBuffer = StringBuffer("")

        try {
            val uri = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, keyWord)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build()
            val url = URL(uri.toString())

            httpURLConnection = url.openConnection() as HttpURLConnection?
            httpURLConnection?.connect()

            val iS = httpURLConnection?.inputStream
            bufferedReader = BufferedReader(InputStreamReader(iS))

            var line:String? = ""
            do {
                stringBuffer.append(line + "\n")
                line = bufferedReader.readLine()
            }while (line != null)
            return if (stringBuffer.isEmpty())
                null
            else
                stringBuffer.toString()
        }catch (e: Exception){
            Log.d("ERROR", e.message.toString())
            return null
        }finally {
            httpURLConnection?.disconnect()
            bufferedReader.close()
        }
    }
}