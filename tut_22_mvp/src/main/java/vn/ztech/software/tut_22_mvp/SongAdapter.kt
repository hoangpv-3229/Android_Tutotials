package vn.ztech.software.tut_22_mvp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(val songs: List<Song>, val onClick: OnClickListener): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var iv: ImageView = view.findViewById(R.id.iv)
        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        fun bind(song: Song){
            iv.setImageResource(R.drawable.ic_launcher_foreground)
            tvName.text = song.name
            tvAuthor.text = song.author

            view.rootView.setOnClickListener{
                onClick.onSongClicked(song)
            }
        }
    }
    interface OnClickListener{
        fun onSongClicked(song: Song)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SongViewHolder(inflater.inflate(R.layout.item_music, parent, false))
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}