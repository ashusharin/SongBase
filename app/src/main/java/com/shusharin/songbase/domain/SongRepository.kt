package com.shusharin.songbase.domain

import com.shusharin.songbase.data.SongDataList

interface SongRepository {
    suspend fun findListSong(): SongDataList
}