package com.shusharin.songbase.domain

import com.shusharin.songbase.data.SongDataListToDomainMapper
import com.shusharin.songbase.data.SongRepository

interface SongsInteractor {
    suspend fun findListSong(): SongDomainList

    class Base(
        private val songRepository: SongRepository,
        private val mapper: SongDataListToDomainMapper,
    ) : SongsInteractor {
        override suspend fun findListSong(): SongDomainList =
            songRepository.findListSong().map(mapper)
    }
}