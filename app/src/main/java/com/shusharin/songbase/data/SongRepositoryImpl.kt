package com.shusharin.songbase.data

import com.shusharin.songbase.data.cache.external.ExternalCacheDataSource
import com.shusharin.songbase.data.cache.internal.InternalCacheDataSource
import com.shusharin.songbase.data.update.Mapper
import com.shusharin.songbase.domain.Song
import com.shusharin.songbase.domain.SongRepository
import com.shusharin.songbase.ui.ResourceProvider

class SongRepositoryImpl(
    private val internalCacheDataSource: InternalCacheDataSource,
    private val externalCacheDataSource: ExternalCacheDataSource,
    private val mapper: Mapper,
    private val resourceProvider: ResourceProvider,
) : SongRepository {

    override suspend fun findListSong(): SongDataList = try {
        val songsDb = internalCacheDataSource.fetchListSong()

        if (songsDb.isEmpty()) {
            val songsExternal = externalCacheDataSource.find()
            internalCacheDataSource.saveListSong(songsExternal)
            SongDataList.Success(songsExternal)
        } else {
            val songsExternal = externalCacheDataSource.find()
            var songsInternal = mapper.mapListDbModelToListEntity(songsDb)
            updateSongs(songsExternal, songsInternal)
            internalCacheDataSource.saveListSong(songsExternal)
            songsInternal =
                mapper.mapListDbModelToListEntity(internalCacheDataSource.fetchListSong())
            SongDataList.Success(songsInternal)
        }
    } catch (e: Exception) {
        SongDataList.Fail(e, resourceProvider)
    }

    private suspend fun updateSongs(
        songsExternal: List<Song>,
        songsInternal: List<Song>,
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