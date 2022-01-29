package com.shusharin.songbase.data.contentResolver

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.shusharin.songbase.data.SongData

// FIXME: 27.01.2022 название класса
interface Finder {
    fun find(): List<SongData>

    class Base(private val contentResolverWrapper: ContentResolverWrapper) : Finder {
        override fun find(): List<SongData> {
            var musicList = mutableListOf<SongData>()
            val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
            val musicCursor = contentResolverWrapper.provideContentResolver()
                .query(musicUri, null, selection, null, null)

            if (musicCursor != null && musicCursor.moveToFirst()) {
                //get columns
                val titleColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val idColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val artistColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                //Todo Added in API level 30
                val bitrateColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.BITRATE)
                val sizeColumn: Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.SIZE)
                val durationColumn: Int =
                    musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                //TODO В зависимости от версии api либо data, либо relative_path
                val uriColumn: Int =
                    musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                //add songs to list
                do {
                    val trackId: Long = musicCursor.getLong(idColumn)
                    val title: String = musicCursor.getString(titleColumn)
                    val artist: String = musicCursor.getString(artistColumn)
//                    val bitrate: Int = musicCursor.getInt(bitrateColumn)
                    val local_path: String = musicCursor.getString(uriColumn)
                    val size: Int = musicCursor.getInt(sizeColumn)
                    val duration: Int = musicCursor.getInt(durationColumn)
                    musicList.add(SongData(
                        _id = 0,
                        track_id = trackId,
                        title = title,
                        artist = artist,
                        bitrate = 0,
                        uri ="" ,
                        size = size,
                        duration = duration,
                        local_path = local_path
                    ))
                    Log.d("треки", "${musicList}")
                } while (musicCursor.moveToNext())
                musicCursor.close()
            }
            return musicList
        }
    }


}