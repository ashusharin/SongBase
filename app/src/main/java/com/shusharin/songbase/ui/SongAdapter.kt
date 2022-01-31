package com.shusharin.songbase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shusharin.songbase.R

class SongAdapter(private val retry: Retry) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private val songs = ArrayList<SongUi>()

    fun update(new: List<SongUi>) {
        songs.clear()
        songs.addAll(new)
        notifyDataSetChanged()
    }

    abstract class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(song: SongUi) {}

        class Base(view: View) : SongViewHolder(view) {
            private val titleSong = itemView.findViewById<TextView>(R.id.title_song)
            private val artistSong = itemView.findViewById<TextView>(R.id.artist_song)
            override fun bind(song: SongUi) {
                song.map(object : SongUi.StringMapper {
                    override fun map(
                        title: String,
                        artist: String,
                    ) {
                        titleSong.text = title
                        artistSong.text = artist
                    }
                })
            }
        }

        class Fail(view: View, private val retry: Retry) : SongViewHolder(view) {
            private val message = itemView.findViewById<TextView>(R.id.messageTextView)
            private val button = itemView.findViewById<Button>(R.id.tryAgainButton)
            override fun bind(song: SongUi) {
                song.map(object : SongUi.StringMapper {
                    override fun map(text: String) {
                        message.text = text
                    }
                })
                button.setOnClickListener { retry.tryAgain() }
            }
        }

        class Progress(view: View) : SongViewHolder(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder =
        when (viewType) {
            0 -> SongViewHolder.Base(R.layout.song_layout.makeView(parent))
            1 -> SongViewHolder.Fail(R.layout.fail_fullscreen.makeView(parent), retry)
            else -> SongViewHolder.Progress(R.layout.progress_fullscreen.makeView(parent))
        }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) =
        holder.bind(songs[position])

    override fun getItemCount(): Int = songs.size
    override fun getItemViewType(position: Int) = when (songs[position]) {
        is SongUi.Base -> 0
        is SongUi.Fail -> 1
        is SongUi.Progress -> 2
    }

    interface Retry {
        fun tryAgain()
    }
}

private fun Int.makeView(parent: ViewGroup) =
    LayoutInflater.from(parent.context).inflate(this, parent, false)