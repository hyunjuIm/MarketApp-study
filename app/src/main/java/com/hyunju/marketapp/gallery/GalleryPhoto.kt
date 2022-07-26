package com.hyunju.marketapp.gallery

import android.net.Uri

data class GalleryPhoto(
    val id: Long,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
    val isSelected: Boolean = false
)
