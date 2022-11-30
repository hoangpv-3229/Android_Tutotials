package vn.ztech.software.testsendintenttoopenwebsite

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var etWeb: EditText
    lateinit var btWeb: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etWeb = findViewById(R.id.etWeb)
        btWeb = findViewById(R.id.btWeb)

        btWeb.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(etWeb.text.toString())
            if (intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }else{
                Toast.makeText(this,"Wrong website address", Toast.LENGTH_LONG).show()
            }

        }
    }
}