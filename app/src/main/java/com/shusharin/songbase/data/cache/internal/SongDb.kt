package com.shusharin.songbase.data.cache.internal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.SongData
import com.shusharin.songbase.data.ToSongMapper

@Entity
data class SongDb(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0,
    var track_id: Long,
    var title: String,
    var artist: String,
    var size: Int,
    var bitrate: Int,
    var duration: Int,
    var local_path: String,
    var uri: String,
) : Abstract.Object<SongData, ToSongMapper> {
    override fun map(mapper: ToSongMapper): SongData =
        SongData(_id, track_id, title, artist, size, bitrate, duration, local_path, uri)
}
