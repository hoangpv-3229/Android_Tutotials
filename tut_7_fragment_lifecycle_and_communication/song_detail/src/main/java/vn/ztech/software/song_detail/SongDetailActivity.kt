package vn.ztech.software.song_detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import vn.ztech.software.song_detail.content.SongUtils

class SongDetailActivity : AppCompatActivity() {
    // SongItem includes the song title and detail.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)
        val toolbar: Toolbar = findViewById(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Show the Up button in the action bar.
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        // This activity displays the detail. In a real-world scenario,
        // get the data from a content repository.
        val songId = intent.getIntExtra(SongUtils.SONG_ID_KEY, 0)
        val fragment = SongDetailFragment.newInstance(songId)
        supportFragmentManager.beginTransaction().add(R.id.song_detail_container, fragment)
            .commit()

    }

    /**
     * Performs action if the user selects the Up button.
     *
     * @param item Menu item selected (Up button)
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            // NavUtils allows users to navigate up one level in the
            // application structure.
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
