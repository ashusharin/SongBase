package com.shusharin.songbase.ui

import com.shusharin.songbase.R
import com.shusharin.songbase.core.Abstract
import com.shusharin.songbase.data.ErrorType
import com.shusharin.songbase.domain.SongDomain
import com.shusharin.songbase.domain.SongDomainToUiMapper

sealed class SongUiList : Abstract.Object<Unit, SongListCommunication> {

    class Success(
        private val songs: List<SongDomain>,
        private val songMapper: SongDomainToUiMapper,
    ) : SongUiList() {
        override fun map(mapper: SongListCommunication) {
            val songUi = songs.map {
                it.map(songMapper)
            }
            mapper.map(songUi)
        }
    }

    class Fail(
        private val errorType: ErrorType,
        private val resourceProvider: ResourceProvider,
    ) : SongUiList() {
        override fun map(mapper: SongListCommunication) {
            val messageId = when (errorType) {
                ErrorType.NO_PERMISSION -> R.string.no_permission_message
                else -> R.string.something_went_wrong
            }
            val message = resourceProvider.getString(messageId)
            mapper.map(listOf(SongUi.Fail(message)))
        }
    }
}