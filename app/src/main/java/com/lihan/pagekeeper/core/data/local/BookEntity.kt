package com.lihan.pagekeeper.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val title: String,
    val author: String,
    val fileUriPath: String,
    val imageFilePath: String,
    val isFavorite: Boolean,
    val isReadFinished: Boolean
)
