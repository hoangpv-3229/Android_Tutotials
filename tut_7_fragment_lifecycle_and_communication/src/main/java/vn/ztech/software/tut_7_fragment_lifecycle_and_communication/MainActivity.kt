package vn.ztech.software.tut_7_fragment_lifecycle_and_communication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity(), BlankFragment.FragmentCallback {
    private lateinit var mButton: Button
    private var isFragmentDisplayed = false
    private var prevChoice = -1
    private var prevRating = -1.0f

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

            prevChoice = savedInstanceState.getInt(BlankFragment.PREV_CHOICE_FLAG)

            prevRating = savedInstanceState.getFloat(BlankFragment.PREV_RATING_FLAG)
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
        add(R.id.fragment_container, BlankFragment.newInstance(prevChoice, prevRating)).
        addToBackStack(BlankFragment::class.qualifiedName).
        commit()
        mButton.text = "Close"
        isFragmentDisplayed = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("STATE", isFragmentDisplayed)
        outState.putInt(BlankFragment.PREV_CHOICE_FLAG, prevChoice)
        outState.putFloat(BlankFragment.PREV_RATING_FLAG, prevRating)
        super.onSaveInstanceState(outState)
    }

    override fun fragmentClose(prevChoice: Int, prevRating: Float) {
        this.prevChoice = prevChoice
        this.prevRating = prevRating
    }
}