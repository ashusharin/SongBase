package com.shusharin.songbase.data

import com.shusharin.songbase.domain.SongDomainList
import com.shusharin.songbase.domain.Song
import com.shusharin.songbase.ui.ResourceProvider

sealed class SongDataList {
    abstract fun map(): SongDomainList

    data class Success(private val listSong: List<Song>) : SongDataList() {
        override fun map(): SongDomainList = SongDomainList.Success(listSong)
    }

    data class Fail(private val e: Exception, private val resourceProvider: ResourceProvider) :
        SongDataList() {
        override fun map(): SongDomainList = SongDomainList.Fail(e,resourceProvider)
    }
}
