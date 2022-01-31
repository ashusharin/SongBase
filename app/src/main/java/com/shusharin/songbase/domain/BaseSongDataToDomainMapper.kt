package com.shusharin.songbase.domain

import com.shusharin.songbase.data.SongDataToDomainMapper

class BaseSongDataToDomainMapper : SongDataToDomainMapper {
    override fun map(title: String, artist: String) = SongDomain(title, artist)
}