package com.shusharin.songbase.data

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.domain.SongDomain

interface SongDataToDomainMapper : Abstract.Mapper {
    fun map(title: String, artist: String): SongDomain
}