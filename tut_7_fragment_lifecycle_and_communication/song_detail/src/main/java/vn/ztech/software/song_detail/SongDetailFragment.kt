package vn.ztech.software.song_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import vn.ztech.software.song_detail.content.SongUtils


class SongDetailFragment : Fragment() {
    var mSong: SongUtils.Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val songId = arguments?.getInt(SongUtils.SONG_ID_KEY, 0)
        songId?.let {
            mSong = SongUtils.SONG_ITEMS.get(it)
        }
        // Show the detail information in a TextView.
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.song_detail, container, false)
        mSong?.let {
            val tvSongDetail = view.findViewById(R.id.song_detail) as TextView
            tvSongDetail.text = it.details
        }
        return view.rootView
    }

    companion object {

        fun newInstance(songId: Int): SongDetailFragment {
            val songDetailFragment = SongDetailFragment()
            val bundle = Bundle()
            bundle.putInt(SongUtils.SONG_ID_KEY, songId)
            songDetailFragment.arguments = bundle
            return songDetailFragment
        }
    }
}