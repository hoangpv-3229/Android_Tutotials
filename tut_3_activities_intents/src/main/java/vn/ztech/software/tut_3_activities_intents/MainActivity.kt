package vn.ztech.software.tut_3_activities_intents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

open class MainActivity : AppCompatActivity() {

    lateinit var btSend: Button
    lateinit var etMsg: EditText
    lateinit var tvMsg: TextView
    open val LAYOUT = R.layout.activity_main
    open val OTHER_ACTIVITY:Any = SecondActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        setViews()
        handleReceivedMsg()
    }
    private fun setViews(){
        btSend = findViewById(R.id.btSend)
        etMsg = findViewById(R.id.etMsg)
        tvMsg = findViewById(R.id.tvMsg)

        btSend.setOnClickListener {
            sendToTheOtherActivity(etMsg.text.toString())
        }
    }

    private fun handleReceivedMsg() {
        val msg = intent.getStringExtra(MSG)
        tvMsg.text = msg
    }

    open fun sendToTheOtherActivity(msg: String) {
        val intent = Intent(this, OTHER_ACTIVITY as Class<*>)
        intent.putExtra(MSG, msg)
        startActivity(intent)
        finish()
    }

    companion object{
        const val MSG = "MSG"
    }
}