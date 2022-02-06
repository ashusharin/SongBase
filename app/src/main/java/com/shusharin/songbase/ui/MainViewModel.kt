package com.shusharin.songbase.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shusharin.songbase.domain.SongsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val songsInteractor: SongsInteractor,
    private val communication: SongListCommunication,
) : ViewModel(
) {

    fun findSongs() {
        communication.map(listOf(SongUi.Progress))
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = songsInteractor.findListSong()
            val resultUi = resultDomain.map()
            withContext(Dispatchers.Main) {
                resultUi.map(communication)
            }
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<SongUi>>) {
        communication.observe(owner, observer)
    }
}