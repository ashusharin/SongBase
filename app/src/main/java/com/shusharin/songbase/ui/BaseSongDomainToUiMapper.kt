package com.shusharin.songbase.ui

import com.shusharin.songbase.domain.SongDomainToUiMapper

class BaseSongDomainToUiMapper : SongDomainToUiMapper {
    override fun map(title: String, artist: String): SongUi = SongUi.Base(title, artist)
}