package com.shusharin.songbase.data

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.domain.SongDomainList
import java.lang.Exception

interface SongDataListToDomainMapper : Abstract.Mapper {

    fun map(songs: List<SongData>): SongDomainList
    fun map(e: Exception): SongDomainList
}