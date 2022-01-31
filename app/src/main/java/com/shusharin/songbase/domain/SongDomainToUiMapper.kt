package com.shusharin.songbase.domain

import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.ui.SongUi

interface SongDomainToUiMapper : Abstract.Mapper {
    fun map(title:String, artist:String) : SongUi
}