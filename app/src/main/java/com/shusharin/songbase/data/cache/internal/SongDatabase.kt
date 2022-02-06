package com.shusharin.songbase.data.cache.internal

class SongDatabase(private val songDAO: DAO) {

    suspend fun getSongFromDB(): List<SongDb> {
        return songDAO.getSongList()
    }

    suspend fun insertSongInDB(songList: List<SongDb>) {
        songDAO.insertSongList(songList)
    }

    suspend fun deleteSongInDB(song: SongDb){
        songDAO.deleteSong(song)
    }

    suspend fun updateSong(song: SongDb){
        songDAO.updateSong(song)
    }

    suspend fun deleteAll(){
        songDAO.deleteAllSong()
    }
}