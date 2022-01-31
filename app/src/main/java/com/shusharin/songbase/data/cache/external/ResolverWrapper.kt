package com.shusharin.songbase.data.cache.external

import android.content.ContentResolver
import android.content.Context

interface ResolverWrapper {
    fun provideContentResolver(): ContentResolver

    class Base(private val context: Context) : ResolverWrapper {
        override fun provideContentResolver(): ContentResolver = context.contentResolver
        }
    }