package com.shusharin.songbase.ui

import com.shusharin.songbase.data.ErrorType
import com.shusharin.songbase.domain.SongDomain
import com.shusharin.songbase.domain.SongDomainListToUiMapper
import com.shusharin.songbase.domain.SongDomainToUiMapper

class BaseSongDomainListToUiMapper(
    private val songMapper: SongDomainToUiMapper,
    private val resourceProvider: ResourceProvider,
) :
    SongDomainListToUiMapper {
    override fun map(songs: List<SongDomain>): SongUiList = SongUiList.Success(songs, songMapper)

    override fun map(errorType: ErrorType): SongUiList =
        SongUiList.Fail(errorType, resourceProvider)
}