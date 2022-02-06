package com.shusharin.songbase.data

import com.shusharin.songbase.data.cache.internal.InternalCacheDataSource
import com.shusharin.songbase.data.cache.internal.SongListCacheMapper
import com.shusharin.songbase.data.cache.external.ExternalCacheDataSource
import java.lang.Exception

interface SongRepository {
    suspend fun findListSong(): SongDataList

    class Base(
        private val internalCacheDataSource: InternalCacheDataSource,
        private val externalCacheDataSource: ExternalCacheDataSource,
        private val cacheMapper: SongListCacheMapper,
    ) : SongRepository {
        override suspend fun findListSong(): SongDataList = try {
            val songsDb = internalCacheDataSource.fetchListSong()

            if (songsDb.isEmpty()) {
                val songsExternal = externalCacheDataSource.find()
                internalCacheDataSource.saveListSong(songsExternal)
                SongDataList.Success(songsExternal)
            } else {
                val songsExternal = externalCacheDataSource.find()
                var songsInternal = cacheMapper.map(songsDb)
                updateSongs(songsExternal, songsInternal)
                internalCacheDataSource.saveListSong(songsExternal)
                songsInternal = cacheMapper.map(internalCacheDataSource.fetchListSong())
                SongDataList.Success(songsInternal)
            }
        } catch (e: Exception) {
            SongDataList.Fail(e)
        }

        private suspend fun updateSongs(
            songsExternal: List<SongData>,
            songsInternal: List<SongData>,
        ) {
            if (songsExternal.isEmpty()) {
                internalCacheDataSource.deleteAll()
            } else {
                songsInternal.forEach { songInternal ->
                    if (!songsExternal.any { songInternal == it }) {
                        songsExternal.forEach { songExternal ->
                            if (songInternal.isSame(songExternal)) internalCacheDataSource.updateSong(
                                songExternal) else
                                internalCacheDataSource.deleteSong(songInternal)
                        }
                    }
                }
            }
        }
    }
}