package vn.ztech.software.tut_17_shared_preference

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    // Current count
    private var mCount = 0

    // Current background color
    private var mColor = 0

    // Text view to display both count and color
    lateinit var mShowCountTextView: TextView

    // Key for current count
    private val COUNT_KEY = "count"

    // Key for current color
    private val COLOR_KEY = "color"

    private lateinit var pref: SharedPreferences
    private val PREF_FILE_NAME = BuildConfig.APPLICATION_ID + "SHARED_PREF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview)
        mColor = ContextCompat.getColor(
            this,
            R.color.default_background
        )

        pref = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)

        // Restore the saved instance state.
        if (pref.contains(COUNT_KEY)){
            mCount = pref.getInt(COUNT_KEY, 0)
            mShowCountTextView.setText(String.format("%s", mCount))
        }
        if (pref.contains(COLOR_KEY)){
            mColor = pref.getInt(COLOR_KEY, 0)
            mShowCountTextView.setBackgroundColor(mColor)
        }
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    fun changeBackground(view: View) {
        val color = (view.getBackground() as ColorDrawable).color
        mShowCountTextView!!.setBackgroundColor(color)
        mColor = color
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    fun countUp(view: View?) {
        mCount++
        mShowCountTextView!!.text = String.format("%s", mCount)
    }

    /**
     * Saves the instance state if the activity is restarted (for example,
     * on device rotation.) Here you save the values for the count and the
     * background color.
     *
     * @param outState The state data.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT_KEY, mCount)
        outState.putInt(COLOR_KEY, mColor)
    }

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    fun reset(view: View?) {
        // Reset count
        mCount = 0
        mShowCountTextView!!.text = String.format("%s", mCount)

        // Reset color
        mColor = ContextCompat.getColor(
            this,
            R.color.default_background
        )
        mShowCountTextView!!.setBackgroundColor(mColor)

        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    override fun onPause() {
        super.onPause()
        val editor = pref.edit()
        editor.putInt(COUNT_KEY, mCount)
        editor.putInt(COLOR_KEY, mColor)
        editor.apply()
    }
}