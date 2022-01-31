package com.shusharin.songbase.data.cache.internal

import androidx.room.*

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongList(songList: List<SongDb>)

    @Query("SELECT * FROM SongDb")
    suspend fun getSongList(): List<SongDb>

    @Delete
    suspend fun deleteSong(song: SongDb)

    @Update
    suspend fun updateSong(song: SongDb)
}