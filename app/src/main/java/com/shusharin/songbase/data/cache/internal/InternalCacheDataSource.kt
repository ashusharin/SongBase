package com.shusharin.songbase.data.cache.internal

import com.shusharin.songbase.data.SongData

interface InternalCacheDataSource {

    suspend fun fetchListSong(): List<SongDb>

    suspend fun saveListSong(songs: List<SongData>)

    suspend fun deleteSong(song: SongData)

    suspend fun updateSong(song: SongData)

    class Base(private val database: SongDatabase, private val toDbMapper: SongDataToDbMapper) :
        InternalCacheDataSource {

        override suspend fun fetchListSong(): List<SongDb> {
            return database.getSongFromDB()

        }

        override suspend fun saveListSong(songs: List<SongData>) {
            database.insertSongInDB(songs.map {
                it.mapTo(toDbMapper)
            })
        }

        override suspend fun deleteSong(song: SongData) {
            database.deleteSongInDB(song.mapTo(toDbMapper))
        }

        override suspend fun updateSong(song: SongData) {
            database.updateSong(song.mapTo(toDbMapper))
        }
    }
}