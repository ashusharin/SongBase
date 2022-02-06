package com.shusharin.songbase.domain

interface SongsInteractor {
    suspend fun findListSong(): SongDomainList

    class Base(
        private val songRepository: SongRepository,
    ) : SongsInteractor {
        override suspend fun findListSong(): SongDomainList = songRepository.findListSong().map()
    }
}