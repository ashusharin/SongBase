package com.shusharin.songbase.data.cache.internal

import com.shusharin.songbase.data.update.Mapper
import com.shusharin.songbase.domain.Song

interface InternalCacheDataSource {

    suspend fun fetchListSong(): List<SongDb>

    suspend fun saveListSong(songs: List<Song>)

    suspend fun deleteSong(song: Song)

    suspend fun updateSong(song: Song)

    suspend fun deleteAll()

    class Base(
        private val database: SongDatabase,
        private val mapper: Mapper,
    ) :
        InternalCacheDataSource {

        override suspend fun fetchListSong(): List<SongDb> {
            return database.getSongFromDB()

        }

        override suspend fun saveListSong(songs: List<Song>) {
            database.insertSongInDB(songs.map {
                mapper.mapEntityToDbModel(it)
            })
        }

        override suspend fun deleteSong(song: Song) {
            database.deleteSongInDB(mapper.mapEntityToDbModel(song))
        }

        override suspend fun updateSong(song: Song) {
            database.updateSong(mapper.mapEntityToDbModel(song))
        }

        override suspend fun deleteAll() {
            database.deleteAll()
        }


    }
}