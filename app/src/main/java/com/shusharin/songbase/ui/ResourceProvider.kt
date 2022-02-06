package com.shusharin.songbase.ui

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {
     fun getString(id: Int): String = context.getString(id)
}