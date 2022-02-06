package com.shusharin.songbase.data.cache.external

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.shusharin.songbase.data.SongData
import java.nio.channels.SelectableChannel
import kotlin.math.abs

interface ExternalCacheDataSource {
    fun find(): List<SongData>

    class Base(private val resolver: ResolverWrapper, private val cursorManager: CursorManager) :
        ExternalCacheDataSource {
        override fun find(): List<SongData> {
            val musicList = mutableListOf<SongData>()
            val collection = cursorManager.provideCollection()
            val projection = cursorManager.provideProjection()
            val query = resolver.provideContentResolver().query(
                collection,
                projection,
                SELECTION,
                null,
                null
            )

            query?.use { cursor ->
                val _idColumn = cursor.getColumnIndexOrThrow(_ID)
                val titleColumn = cursor.getColumnIndexOrThrow(TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(ARTIST)
                val id_track = cursor.getColumnIndexOrThrow(TRACK)
                val sizeColumn = cursor.getColumnIndexOrThrow(SIZE)
                val durationColumn =
                    cursor.getColumnIndexOrThrow(DURATION)
                val localPathColumn =
                    cursor.getColumnIndexOrThrow(cursorManager.providePath())
                val typeColumn =
                    cursor.getColumnIndexOrThrow(MIME_TYPE)
                val bitrateColumn =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) cursor.getColumnIndexOrThrow(
                        BITRATE) else null

                while (cursor.moveToNext()) {
                    val _id = cursor.getLong(_idColumn)
                    val trackId = cursor.getLong(id_track)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val local_path = cursor.getString(localPathColumn)
                    val size = cursor.getInt(sizeColumn)
                    val duration = cursor.getInt(durationColumn)
                    val bitrate = if (bitrateColumn == null) calculateBitrate(size, duration) else
                        cursor.getInt(bitrateColumn)
                    val uri = ContentUris.withAppendedId(
                        collection, _id)
                    val mimeType = getMimeType(cursor, typeColumn)

                    if (mimeType.toString() in types && duration > TEN_SECOND) {
                        val pathSplit = local_path.split("/")
                        if (isMusic(pathSplit)) {
                            musicList.add(SongData(_id = _id,
                                track_id = trackId,
                                title = title,
                                artist = artist,
                                bitrate = bitrate,
                                uri = uri.toString(),
                                size = size,
                                duration = duration,
                                local_path = local_path))
                        }
                    }
                }
            }
            return musicList
        }

        private fun isMusic(path: List<String>): Boolean {
            var result = true
            for (name in path) {
                if (PATH_NAME.contains(name)) {
                    result = false
                }
            }
            return result
        }

        private fun getMimeType(cursor: Cursor, typeColumn: Int): String? {
            val type = cursor.getString(typeColumn)
            val mimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
            return mimeType
        }

        private fun calculateBitrate(size: Int, duration: Int): Int {
            val bitrateApproximate = (size * BITE_IN_BYTE) / (duration / MILLISECONDS_IN_SECOND)
            var result = 0
            val array = arrayListOf<Int>()
            for (i in BITRATE_VALUE) {
                array += abs((i - bitrateApproximate))
            }
            val min = array.minOrNull()
            for (i in array.indices) {
                if (min == array[i]) {
                    result = BITRATE_VALUE[i]
                }
            }
            return result
        }
    }

    companion object {
        val types = arrayOf("mp4",
            "mp4a",
            "fmp4",
            "mp3",
            "ogg",
            "wav",
            "flac")

        val BITRATE_VALUE = arrayOf(160000, 192000, 256000, 320000)
        const val SELECTION = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        const val _ID = MediaStore.Audio.Media._ID
        const val TITLE = MediaStore.Audio.Media.TITLE
        const val ARTIST = MediaStore.Audio.Media.ARTIST
        const val TRACK = MediaStore.Audio.Media.TRACK
        const val BITRATE = MediaStore.Audio.Media.BITRATE
        const val SIZE = MediaStore.Audio.Media.SIZE
        const val DURATION = MediaStore.Audio.Media.DURATION
        const val DATA = MediaStore.Audio.Media.DATA
        const val RELATIVE_PATH = MediaStore.Audio.Media.RELATIVE_PATH
        const val MIME_TYPE = MediaStore.Audio.Media.MIME_TYPE

        const val BITE_IN_BYTE = 8
        const val MILLISECONDS_IN_SECOND = 1000
        const val TEN_SECOND = 1000

        val PATH_NAME = arrayOf(
            "sound_recorder",
            "voice_recorder",
            "recordings",
            "Recordings",
            "Sounds",
            "Sound",
            "Rec",
            "Soundrecorder",
            "Call",
            "VoiceNotes",
            "Voice",
            "voice"
        )

    }
}


