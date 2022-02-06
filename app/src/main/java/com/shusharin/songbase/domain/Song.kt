package com.shusharin.songbase.domain

data class Song(
    val _id: Long,
    val track_id: Long,
    val title: String,
    val artist: String,
    val size: Int,
    val bitrate: Int,
    val duration: Int,
    val local_path: String,
    val uri: String,
){
    fun isSame(song: Song): Boolean =
        (song.local_path == local_path && song.title == title && song.duration != duration)

}