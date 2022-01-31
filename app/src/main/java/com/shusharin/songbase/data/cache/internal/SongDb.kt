package com.shusharin.songbase.data.cache.internal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.SongData
import com.shusharin.songbase.data.ToSongMapper

@Entity
data class SongDb(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val track_id: Long,
    val title: String,
    val artist: String,
    val size: Int,
    val bitrate: Int,
    val duration: Int,
    val local_path: String,
    val uri: String,
) : Abstract.Object<SongData, ToSongMapper> {
    override fun map(mapper: ToSongMapper): SongData =
        SongData(_id, track_id, title, artist, size, bitrate, duration, local_path, uri)
}
