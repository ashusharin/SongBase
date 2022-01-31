package com.shusharin.songbase.domain

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.ErrorType
import com.shusharin.songbase.data.SongData
import com.shusharin.songbase.data.SongDataToDomainMapper
import com.shusharin.songbase.ui.SongUiList
import java.lang.Exception
import java.net.UnknownHostException

sealed class SongDomainList : Abstract.Object<SongUiList, SongDomainListToUiMapper> {

    class Success(
        private val songs: List<SongData>,
        private val songMapper: SongDataToDomainMapper,
    ) : SongDomainList() {
        override fun map(mapper: SongDomainListToUiMapper): SongUiList = mapper.map(songs.map {
            it.map(songMapper)
        })
    }

    class Fail(private val e: Exception) : SongDomainList() {
        override fun map(mapper: SongDomainListToUiMapper): SongUiList = mapper.map(
            when (e) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is SecurityException -> ErrorType.NO_PERMISSION
                else -> ErrorType.GENERIC_ERROR
            }
        )
    }
}