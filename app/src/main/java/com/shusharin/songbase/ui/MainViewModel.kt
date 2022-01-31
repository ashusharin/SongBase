package com.shusharin.songbase.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shusharin.songbase.domain.SongDomainListToUiMapper
import com.shusharin.songbase.domain.SongsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val songsInteractor: SongsInteractor,
    private val communication: SongListCommunication,
    private val mapper: SongDomainListToUiMapper,
) : ViewModel(
) {

    fun findSongs() {
        Log.d("данные", "findsongs вызван ")
        communication.map(listOf(SongUi.Progress))
        viewModelScope.launch(Dispatchers.IO) {
            val resultDomain = songsInteractor.findListSong()
            Log.d("данные", "$resultDomain")
            val resultUi = resultDomain.map(mapper)
            withContext(Dispatchers.Main) {
                resultUi.map(communication)
            }
        }

    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<SongUi>>) {
        communication.observe(owner, observer)
    }

}