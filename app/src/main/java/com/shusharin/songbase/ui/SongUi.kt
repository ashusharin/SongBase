package com.shusharin.songbase.ui

import com.shusharin.songbase.core.Abstract

sealed class SongUi : Abstract.Object<Unit, SongUi.StringMapper> {
    override fun map(mapper: StringMapper) = Unit

    object Progress : SongUi()

    class Base(
        private val title: String,
        private val artist: String,
    ) : SongUi() {
        override fun map(mapper: StringMapper) = mapper.map(title, artist)
    }

    class Fail(private val message: String) : SongUi() {
        override fun map(mapper: StringMapper) = mapper.map(message)
    }

    interface StringMapper : Abstract.Mapper {
        fun map(title: String, artist: String) = Unit
        fun map(message: String) = Unit
    }
}