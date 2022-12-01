package vn.ztech.software.tut_8_drawables_styles_themes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    var scoreTeam1 = 0
    var scoreTeam2 = 0
    val SCORE_TEAM_1 = "SCORE_TEAM_1"
    val SCORE_TEAM_2 = "SCORE_TEAM_2"
    lateinit var tvTeam1: TextView
    lateinit var tvTeam2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTeam1 = findViewById(R.id.score_1)
        tvTeam2 = findViewById(R.id.score_2)

        savedInstanceState?.let {
            if (it.containsKey(SCORE_TEAM_1)){
                scoreTeam1 = it.getInt(SCORE_TEAM_1)
                tvTeam1.text = scoreTeam1.toString()
            }
            if (it.containsKey(SCORE_TEAM_2)){
                scoreTeam2 = it.getInt(SCORE_TEAM_2)
                tvTeam2.text = scoreTeam2.toString()
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SCORE_TEAM_1, scoreTeam1)
        outState.putInt(SCORE_TEAM_2, scoreTeam2)

        super.onSaveInstanceState(outState)
    }

    fun minusScore(view: View) {
        when(view.id){
            R.id.decreaseTeam1 ->{
                scoreTeam1--
                tvTeam1.text = scoreTeam1.toString()

            }
            R.id.decreaseTeam2 ->{
                scoreTeam2--
                tvTeam2.text = scoreTeam2.toString()
            }
        }
    }
    fun plusScore(view: View) {
        when(view.id){
            R.id.increaseTeam1 ->{
                scoreTeam1++
                tvTeam1.text = scoreTeam1.toString()
            }
            R.id.increaseTeam2 ->{
                scoreTeam2++
                tvTeam2.text = scoreTeam2.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        val nightMode = AppCompatDelegate.getDefaultNightMode()
        //Set the theme mode for the restarted activity.
        //Set the theme mode for the restarted activity.
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu?.findItem(R.id.night)?.title = "Day"
        } else {
            menu?.findItem(R.id.night)?.title = "Night"
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.night){
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            //Set the theme mode for the restarted activity.
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            // Recreate the activity for the theme change to take effect.
            recreate()
        }
        return true
    }
}