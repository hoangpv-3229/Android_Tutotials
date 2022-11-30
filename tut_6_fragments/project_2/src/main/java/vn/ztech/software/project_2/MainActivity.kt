package vn.ztech.software.project_2

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mButton: Button
    private var isFragmentDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mButton = findViewById(R.id.btOpenClose)
        mButton.setOnClickListener {
            if (isFragmentDisplayed){
                closeFragment()
            }else{
                openFragment()
            }
        }

        if (savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean("STATE")
            if (isFragmentDisplayed) mButton.text = "Close"
        }

    }

    private fun closeFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
            mButton.text = "Open"
            isFragmentDisplayed = false
        }
    }

    private fun openFragment() {
        supportFragmentManager.beginTransaction().
            add(R.id.fragment_container, BlankFragment.newInstance()).
            addToBackStack(BlankFragment::class.qualifiedName).
            commit()
        mButton.text = "Close"
        isFragmentDisplayed = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("STATE", isFragmentDisplayed)
        super.onSaveInstanceState(outState)
    }
}