package com.shusharin.songbase.domain

import com.shusharin.songbase.data.ErrorType
import com.shusharin.songbase.ui.ResourceProvider
import com.shusharin.songbase.ui.SongUiList
import java.net.UnknownHostException

sealed class SongDomainList {
    abstract fun map(): SongUiList

    class Success(
        private val songs: List<Song>,
    ) : SongDomainList() {
        override fun map(): SongUiList = SongUiList.Success(songs)
    }

    class Fail(private val e: Exception, private val resourceProvider: ResourceProvider) :
        SongDomainList() {
        override fun map(): SongUiList {
            val errorType = when (e) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is SecurityException -> ErrorType.NO_PERMISSION
                else -> ErrorType.GENERIC_ERROR
            }
            return SongUiList.Fail(errorType, resourceProvider)
        }

    }
}