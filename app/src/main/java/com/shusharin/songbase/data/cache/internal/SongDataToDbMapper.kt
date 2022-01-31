package com.shusharin.songbase.data.cache.internal

import com.shusharin.songbase.core.Abstract

interface SongDataToDbMapper : Abstract.Mapper {
    fun mapToDb(
        _id: Long,
        track_id: Long,
        title: String,
        artist: String,
        size: Int,
        bitrate: Int,
        duration: Int,
        local_path: String,
        uri: String,
    ): SongDb

    class Base : SongDataToDbMapper {
        override fun mapToDb(
            _id: Long,
            track_id: Long,
            title: String,
            artist: String,
            size: Int,
            bitrate: Int,
            duration: Int,
            local_path: String,
            uri: String,
        ): SongDb = SongDb(_id,
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