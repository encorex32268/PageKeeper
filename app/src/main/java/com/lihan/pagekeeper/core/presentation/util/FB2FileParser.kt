package com.lihan.pagekeeper.core.presentation.util

import android.net.Uri
import com.lihan.pagekeeper.core.presentation.util.model.EpubMetadata
import com.lihan.pagekeeper.core.presentation.util.model.FB2Metadata

interface FB2FileParser {
    suspend fun parse(uri: Uri): FB2Metadata?
}