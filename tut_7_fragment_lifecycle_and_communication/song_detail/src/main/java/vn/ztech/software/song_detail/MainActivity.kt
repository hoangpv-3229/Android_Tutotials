package vn.ztech.software.song_detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import vn.ztech.software.song_detail.content.SongUtils

class MainActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    /**
     * Sets up a song list as a RecyclerView.
     *
     * @param savedInstanceState
     */
    private var isTwoPanel = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        // Set the toolbar as the app bar.
        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(title)

        // Get the song list as a RecyclerView.
        val recyclerView: RecyclerView = findViewById(R.id.song_list) as RecyclerView
        recyclerView.setAdapter(SimpleItemRecyclerViewAdapter(SongUtils.SONG_ITEMS))

        if(findViewById<FrameLayout>(R.id.song_detail_container)!=null){
            isTwoPanel = true
        }
    }

    /**
     * The ReyclerView for the song list.
     */
    internal inner class SimpleItemRecyclerViewAdapter(items: List<SongUtils.Song>) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder?>() {
        private val mValues: List<SongUtils.Song>

        /**
         * This method inflates the layout for the song list.
         * @param parent ViewGroup into which the new view will be added.
         * @param viewType The view type of the new View.
         * @return
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.song_list_content, parent, false)
            return ViewHolder(view)
        }

        /**
         * This method implements a listener with setOnClickListener().
         * When the user taps a song title, the code checks if mTwoPane
         * is true, and if so uses a fragment to show the song detail.
         * If mTwoPane is not true, it starts SongDetailActivity
         * using an intent with extra data about which song title was selected.
         *
         * @param holder   ViewHolder
         * @param position Position of the song in the array.
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = mValues[position]
            holder.mIdView.text = (position + 1).toString()
            holder.mContentView.setText(mValues[position].song_title)
            holder.mView.setOnClickListener { v ->
                if(isTwoPanel){
                    sendPosToFragment(holder)
                }else{
                    openNewActivity(v, holder)
                }

            }
        }

        private fun sendPosToFragment(holder: MainActivity.SimpleItemRecyclerViewAdapter.ViewHolder) {
            val fragment = SongDetailFragment.newInstance(holder.getAdapterPosition())
            supportFragmentManager.beginTransaction().replace(R.id.song_detail_container, fragment)
                .addToBackStack(SongDetailFragment::class.qualifiedName)
                .commit()
        }

        private fun openNewActivity(v: View, holder: ViewHolder) {
            val context = v.context
            val intent = Intent(
                context,
                SongDetailActivity::class.java
            )
            intent.putExtra(
                SongUtils.SONG_ID_KEY,
                holder.getAdapterPosition()
            )
            context.startActivity(intent)
        }

        /**
         * Get the count of song list items.
         * @return
         */
        override fun getItemCount(): Int {
            return mValues.size
        }

        /**
         * ViewHolder describes an item view and metadata about its place
         * within the RecyclerView.
         */
        internal inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(
            mView
        ) {
            val mIdView: TextView
            val mContentView: TextView
            var mItem: SongUtils.Song? = null

            init {
                mIdView = mView.findViewById<View>(R.id.id) as TextView
                mContentView = mView.findViewById<View>(R.id.content) as TextView
            }
        }

        init {
            mValues = items
        }
    }
}