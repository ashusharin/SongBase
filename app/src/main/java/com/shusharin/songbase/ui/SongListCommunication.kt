package com.shusharin.songbase.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.shusharin.songbase.core.Abstract

interface SongListCommunication : Abstract.Mapper {
    fun map(songs: List<SongUi>)
    fun observe(owner: LifecycleOwner, observer: Observer<List<SongUi>>)

    class Base : SongListCommunication {
        private val listLiveData = MutableLiveData<List<SongUi>>()
        override fun map(songs: List<SongUi>) {
            listLiveData.value = songs
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<SongUi>>) {
            listLiveData.observe(owner, observer)
        }
    }
}