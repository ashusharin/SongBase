package com.shusharin.songbase.data.cache.internal

import com.shusharin.songbase.data.update.Mapper
import com.shusharin.songbase.domain.Song

class InternalCacheDataSource(
    private val database: SongDatabase,
    private val mapper: Mapper,
) {

    suspend fun fetchListSong(): List<SongDb> {
        return database.getSongFromDB()

    }

    suspend fun saveListSong(songs: List<Song>) {
        database.insertSongInDB(songs.map {
            mapper.mapEntityToDbModel(it)
        })
    }

    suspend fun deleteSong(song: Song) {
        database.deleteSongInDB(mapper.mapEntityToDbModel(song))
    }

    suspend fun updateSong(song: Song) {
        database.updateSong(mapper.mapEntityToDbModel(song))
    }

    suspend fun deleteAll() {
        database.deleteAll()
    }
}
