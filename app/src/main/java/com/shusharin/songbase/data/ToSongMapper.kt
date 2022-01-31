package com.shusharin.songbase.data

import com.shusharin.songbase.core.Abstract

interface ToSongMapper : Abstract.Mapper {

    fun map(
        _id: Long,
        track_id: Long,
        title: String,
        artist: String,
        size: Int,
        bitrate: Int,
        duration: Int,
        local_path: String,
        uri: String,
    ): SongData

    class Base() : ToSongMapper {
        override fun map(
            _id:Long,
            track_id: Long,
            title: String,
            artist: String,
            size: Int,
            bitrate: Int,
            duration: Int,
            local_path: String,
            uri: String,
        ): SongData = SongData(_id,
            track_id,
            title,
            artist,
            size,
            bitrate,
            duration,
            local_path,
            uri)

    }
}