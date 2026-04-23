package com.lihan.pagekeeper.core.presentation.util

import android.net.Uri
import com.lihan.pagekeeper.core.presentation.util.model.EpubMetadata

interface EpubMetadataParser {
    suspend fun parseEpubFile(uri: Uri): EpubMetadata?
}