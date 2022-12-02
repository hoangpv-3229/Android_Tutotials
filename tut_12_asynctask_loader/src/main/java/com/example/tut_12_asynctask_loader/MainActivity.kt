package com.example.tut_12_asynctask_loader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv)
        val bt = findViewById<Button>(R.id.bt)
        bt.setOnClickListener {
            CountNumAsyncTask( tv).execute(25)
        }
    }

    class CountNumAsyncTask( val tvView: TextView): AsyncTask<Int, Int, String>(){
        lateinit var  tv: WeakReference<TextView>;
        init {
            tv = WeakReference(tvView)
        }
        override fun onPreExecute() {
            super.onPreExecute()
            tv.get()?.text = "Start coounting"
        }

        override fun doInBackground(vararg p0: Int?): String {
            val p1 = p0[0]?:10
            for (i in 0..p1){
                try {
                    Thread.sleep(500)
                }catch (e: java.lang.Exception){
                    Log.d("Counting", e.toString())
                }
                publishProgress(i)
                Log.d("Counting", i.toString())
            }
            return "Done Counting"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            tv.get()?.text = "Number: ${values[0]}"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            tv.get()?.text = result
        }
    }
}