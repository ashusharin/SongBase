package com.shusharin.songbase.core

import android.app.Application
import androidx.room.Room
import com.shusharin.songbase.data.SongRepositoryImpl
import com.shusharin.songbase.data.cache.external.CursorManager
import com.shusharin.songbase.data.cache.external.ExternalCacheDataSource
import com.shusharin.songbase.data.cache.external.ResolverWrapper
import com.shusharin.songbase.data.cache.internal.AppDatabase
import com.shusharin.songbase.data.cache.internal.InternalCacheDataSource
import com.shusharin.songbase.data.cache.internal.SongDatabase
import com.shusharin.songbase.data.update.Mapper
import com.shusharin.songbase.domain.SongsInteractor
import com.shusharin.songbase.ui.MainViewModel
import com.shusharin.songbase.ui.ResourceProvider
import com.shusharin.songbase.ui.SongListCommunication

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
        val mapper = Mapper()
        val cacheDataSource = InternalCacheDataSource.Base(songDatabase, mapper)
        val cursorManager = CursorManager.Base()
        val externalCacheDataSource = ExternalCacheDataSource.Base(content, cursorManager)
//        val cacheMapper = SongListCacheMapper.Base(toSongMapper)
        val resourceProvider = ResourceProvider.Base(this)
        val songRepository =
            SongRepositoryImpl(cacheDataSource, externalCacheDataSource, mapper, resourceProvider)
        val songsInteractor = SongsInteractor.Base(songRepository)
        val communication = SongListCommunication.Base()
        viewModel = MainViewModel(
            songsInteractor,
            communication,
        )

    }
}