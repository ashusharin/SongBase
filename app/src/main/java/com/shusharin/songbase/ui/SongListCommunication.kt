package com.shusharin.songbase.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.shusharin.songbase.core.Abstract

class SongListCommunication : Abstract.Mapper {
    private val listLiveData = MutableLiveData<List<SongUi>>()
    fun map(songs: List<SongUi>) {
        listLiveData.value = songs
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<SongUi>>) {
        listLiveData.observe(owner, observer)
    }
}