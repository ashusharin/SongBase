package com.shusharin.songbase.data

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.domain.SongDomainList
import java.lang.Exception

sealed class SongDataList : Abstract.Object<SongDomainList, SongDataListToDomainMapper> {


    data class Success(private val listSong : List<SongData>) : SongDataList() {
        override fun map(mapper: SongDataListToDomainMapper): SongDomainList =mapper.map(listSong)
    }

    data class Fail(private val e : Exception) : SongDataList() {
    override fun map(mapper: SongDataListToDomainMapper): SongDomainList {
        return mapper.map(e)
    }
    }
}