package com.shusharin.songbase.data.cache.external

import android.content.ContentResolver
import android.content.Context

class ResolverWrapper(private val context: Context) {
    fun provideContentResolver(): ContentResolver = context.contentResolver

}