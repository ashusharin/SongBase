package com.shusharin.songbase.domain

import com.shusharin.songbase.data.SongData
import com.shusharin.songbase.data.SongDataListToDomainMapper
import com.shusharin.songbase.data.SongDataToDomainMapper
import java.lang.Exception

class BaseSongDataListToDomainMapper(private val songMapper: SongDataToDomainMapper) :
    SongDataListToDomainMapper {
    override fun map(songs: List<SongData>) = SongDomainList.Success(songs, songMapper)
    override fun map(e: Exception): SongDomainList = SongDomainList.Fail(e)
}