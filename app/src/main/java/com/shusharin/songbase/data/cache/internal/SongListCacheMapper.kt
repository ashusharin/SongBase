package com.shusharin.songbase.data.cache.internal

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.SongData
import com.shusharin.songbase.data.ToSongMapper

interface SongListCacheMapper {
    fun map(songs: List<Abstract.Object<SongData, ToSongMapper>>): List<SongData>

    class Base(private val mapper: ToSongMapper) : SongListCacheMapper {
        override fun map(songs: List<Abstract.Object<SongData, ToSongMapper>>): List<SongData> =
            songs.map { songDB ->
                songDB.map(mapper)
            }
    }
}