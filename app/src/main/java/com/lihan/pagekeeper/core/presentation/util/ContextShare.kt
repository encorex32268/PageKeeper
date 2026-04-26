@file:OptIn(ExperimentalUuidApi::class)

package com.lihan.pagekeeper.core.presentation.util

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns



import androidx.core.content.FileProvider
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


fun Context.shareUrisSafely(uriStrings: List<String>) {
    if (uriStrings.isEmpty()) return

    val uris = uriStrings.mapNotNull { path ->
        try {
            val file = File(path)
            if (file.exists()) {
                FileProvider.getUriForFile(
                    this,
                    "${packageName}.fileprovider",
                    file
                )
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    if (uris.isEmpty()) return

    val shareIntent = Intent().apply {
        if (uris.size == 1) {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uris[0])
        } else {
            action = Intent.ACTION_SEND_MULTIPLE
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
        }
        type = "application/x-fictionbook+xml"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    // 使用 ClipData 確保權限和檔名正確傳遞
    val clipData = ClipData.newRawUri("Books", uris[0])
    for (i in 1 until uris.size) {
        clipData.addItem(ClipData.Item(uris[i]))
    }
    shareIntent.clipData = clipData

    startActivity(Intent.createChooser(shareIntent, "分享書籍"))
}

fun Context.getFileNameFromContentUri(uri: Uri): String? {
    var name: String? = null
    try {
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = cursor.getString(nameIndex)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return name
}