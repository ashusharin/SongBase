package com.shusharin.songbase.domain

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.ErrorType
import com.shusharin.songbase.ui.SongUiList

interface SongDomainListToUiMapper : Abstract.Mapper {
    fun map(songs: List<SongDomain>): SongUiList
    fun map(errorType: ErrorType): SongUiList
}