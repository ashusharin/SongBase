package com.shusharin.songbase.data.update

import com.shusharin.songbase.data.cache.internal.SongDb
import com.shusharin.songbase.domain.Song

class Mapper {

    fun mapEntityToDbModel(song: Song): SongDb = SongDb(
        _id = song._id,
        track_id = song._id,
        title = song.title,
        artist = song.artist,
        size = song.size,
        bitrate = song.bitrate,
        duration = song.duration,
        local_path = song.local_path,
        uri = song.uri
    )


    fun mapDbModelToEntity(songDb: SongDb): Song = Song(
        _id = songDb._id,
        track_id = songDb._id,
        title = songDb.title,
        artist = songDb.artist,
        size = songDb.size,
        bitrate = songDb.bitrate,
        duration = songDb.duration,
        local_path = songDb.local_path,
        uri = songDb.uri
    )

    fun mapListDbModelToListEntity(list: List<SongDb>) = list.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(list: List<Song>) = list.map {
        mapEntityToDbModel(it)
    }
}