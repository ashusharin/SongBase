package com.shusharin.songbase.data.cache.external

import android.net.Uri
import android.os.Build
import android.provider.MediaStore

interface CursorManager {

    fun provideProjection(): Array<String?>
    fun provideCollection(): Uri
    fun providePath(): String

    class Base() : CursorManager {
        override fun provideProjection(): Array<String?> {

            val pathRequest = providePath()
            val bitrateRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ExternalCacheDataSource.BITRATE
            } else {
                null
            }

            return arrayOf(
                ExternalCacheDataSource._ID,
                ExternalCacheDataSource.TITLE,
                ExternalCacheDataSource.ARTIST,
                ExternalCacheDataSource.SIZE,
                ExternalCacheDataSource.DURATION,
                ExternalCacheDataSource.MIME_TYPE,
                ExternalCacheDataSource.TRACK,
                bitrateRequest,
                pathRequest
            )
        }

        override fun providePath(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ExternalCacheDataSource.RELATIVE_PATH
        } else {
            ExternalCacheDataSource.DATA
        }


        override fun provideCollection(): Uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
    }
}
