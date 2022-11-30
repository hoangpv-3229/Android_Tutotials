package vn.ztech.software.tut_5_impicit_intents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vn.ztech.software.tut_5_impicit_intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent()
    }
    private fun handleIntent() {
        intent.data?.let {
            Toast.makeText(this, "Received intent ${it}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setViews() {
        binding.btWeb.setOnClickListener {
            openWebsite(binding.etWeb.text.toString())
        }
        binding.btLocation.setOnClickListener {
            openLocation(binding.etLocation.text.toString())
        }
        binding.btShare.setOnClickListener {
            shareText(binding.etShare.text.toString())
        }
    }

    private fun openWebsite(text: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
        startAct(intent, "Wrong website format")
    }

    private fun openLocation(text: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getUriTextFromAddress(text)))
        startAct(intent, "Wrong location format")
    }

    private inline fun getUriTextFromAddress(text: String): String = "geo:0,0?q=${Uri.encode(text)}"
    /**any string must be encoded in the uri passed for GG map intents*/

    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startAct(intent, "Wrong text format")
    }

    private fun startAct(intent: Intent, errorString: String) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, errorString, Toast.LENGTH_LONG).show()
        }
    }
}