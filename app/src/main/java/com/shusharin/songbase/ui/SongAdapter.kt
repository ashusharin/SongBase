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
                    override fun setupText(
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
                    override fun setupText(text: String) {
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
            BASE -> SongViewHolder.Base(LayoutInflater.from(parent.context)
                .inflate(R.layout.song_layout, parent, false))
            FAIL -> SongViewHolder.Fail(LayoutInflater.from(parent.context)
                .inflate(R.layout.fail_fullscreen, parent, false), retry)
            else -> SongViewHolder.Progress(LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_fullscreen, parent, false))
        }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) =
        holder.bind(songs[position])

    override fun getItemCount(): Int = songs.size
    override fun getItemViewType(position: Int) = when (songs[position]) {
        is SongUi.Base -> BASE
        is SongUi.Fail -> FAIL
        is SongUi.Progress -> PROGRESS
    }

    interface Retry {
        fun tryAgain()
    }

    companion object {
        private val BASE = 0
        private val FAIL = 1
        private val PROGRESS = 2
    }
}