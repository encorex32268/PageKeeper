package com.lihan.pagekeeper.core.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.shareUris(
    uris: ArrayList<Uri>
){
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND_MULTIPLE
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        type = "application/x-fictionbook+xml"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    val shareTitle = if (uris.size == 1) "Share Book" else "Share Books"
    this.startActivity(Intent.createChooser(shareIntent,shareTitle))
}