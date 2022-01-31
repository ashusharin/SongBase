package com.shusharin.songbase.core

import android.app.Application
import androidx.room.Room
import com.shusharin.songbase.data.SongRepository
import com.shusharin.songbase.data.ToSongMapper
import com.shusharin.songbase.data.cache.external.CursorManager
import com.shusharin.songbase.data.cache.external.ResolverWrapper
import com.shusharin.songbase.data.cache.external.ExternalCacheDataSource
import com.shusharin.songbase.data.cache.internal.*
import com.shusharin.songbase.domain.BaseSongDataListToDomainMapper
import com.shusharin.songbase.domain.BaseSongDataToDomainMapper
import com.shusharin.songbase.domain.SongsInteractor
import com.shusharin.songbase.ui.*

class SongBaseApp : Application() {
    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        val content = ResolverWrapper.Base(this)
        val appDatabase = Room
            .databaseBuilder(this, AppDatabase::class.java, "Song.db")
            .fallbackToDestructiveMigration()
            .build()
        val dao = appDatabase.dao()
        val songDatabase = SongDatabase(dao)
        val cacheDataSource = InternalCacheDataSource.Base(songDatabase, SongDataToDbMapper.Base())
        val cursorManager = CursorManager.Base()
        val externalCacheDataSource = ExternalCacheDataSource.Base(content,cursorManager)
        val toSongMapper = ToSongMapper.Base()
        val cacheMapper = SongListCacheMapper.Base(toSongMapper)
        val songRepository =
            SongRepository.Base(cacheDataSource, externalCacheDataSource, cacheMapper)
        val songsInteractor = SongsInteractor.Base(songRepository,
            BaseSongDataListToDomainMapper(BaseSongDataToDomainMapper()))
        val communication = SongListCommunication.Base()
        viewModel = MainViewModel(songsInteractor,
            communication,
            BaseSongDomainListToUiMapper(BaseSongDomainToUiMapper(), ResourceProvider.Base(this)))

    }
}