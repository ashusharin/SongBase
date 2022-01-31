package com.shusharin.songbase.domain

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.ui.SongUi

class SongDomain(private val title: String, private val artist: String) :
    Abstract.Object<SongUi, SongDomainToUiMapper> {
    override fun map(mapper: SongDomainToUiMapper): SongUi = mapper.map(title, artist)
}