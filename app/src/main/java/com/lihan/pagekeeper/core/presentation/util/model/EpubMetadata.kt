package com.lihan.pagekeeper.core.presentation.util.model

import android.graphics.Bitmap

data class EpubMetadata(
    val title: String? = null,
    val author: String? = null,
    val cover: Bitmap? = null,
)