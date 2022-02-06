package com.shusharin.songbase.data.cache.internal

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    var uri: String,)

