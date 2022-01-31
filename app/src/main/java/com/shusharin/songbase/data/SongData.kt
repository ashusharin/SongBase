package com.shusharin.songbase.data

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.cache.internal.SongDataToDbMapper
import com.shusharin.songbase.data.cache.internal.SongDb
import com.shusharin.songbase.domain.SongDomain

data class SongData(
    private val _id: Long,
    private val track_id: Long,
    private val title: String,
    private val artist: String,
    private val size: Int,
    private val bitrate: Int,
    private val duration: Int,
    private val local_path: String,
    private val uri: String,
) : ToSongDb<SongDb, SongDataToDbMapper>, Abstract.Object<SongDomain, SongDataToDomainMapper>,
    Compare {
    override fun map(mapper: SongDataToDomainMapper): SongDomain = mapper.map(title, artist)
    override fun mapTo(mapper: SongDataToDbMapper): SongDb = mapper.mapToDb(_id,
        track_id,
        title,
        artist,
        size,
        bitrate,
        duration,
        local_path,
        uri)

    override fun isSame(song: SongData): Boolean =
        (song.local_path == local_path && song.title == title && song.duration != duration)
}

interface ToSongDb<T, M : Abstract.Mapper> {

    fun mapTo(mapper: M): T
}

interface Compare {
    fun isSame(song: SongData): Boolean
}